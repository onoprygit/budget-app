package com.onopry.budgetapp.model.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.repo.FirebaseHelper
import com.onopry.budgetapp.utils.FIREBASE
import com.onopry.budgetapp.utils.LogTags
import com.onopry.budgetapp.utils.TARGET
import com.onopry.budgetapp.utils.TargetNotFoundException
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

typealias TargetListener = (target: List<TargetDTO>) -> Unit

class TargetService @Inject constructor(
    private val authRepository: AuthRepository,
    private val categoriesService: CategoriesService
) {
    private var targetList = mutableListOf<TargetDTO>()
    private val listeners = mutableSetOf<TargetListener>()

    private val _targets = MutableLiveData<List<TargetDTO>>()
    val targets: LiveData<List<TargetDTO>> = _targets

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefTargets = dbRef.child(FirebaseHelper.TARGETS_KEY).child(authRepository.user.value!!.uid)
    private val dbRefTargetsCompleted = dbRef.child(FirebaseHelper.COMPLETED_TARGETS_KEY)

    init {
        //loadTargets()
        loadFirebase()
    }

    private fun loadFirebase(){
        val uid = authRepository.user.value?.uid
        dbRef.child(FirebaseHelper.TARGETS_KEY).child(uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<TargetDTO>()
                    snapshot.children.mapNotNull { targetSnapshot ->
                        Log.d(LogTags.FIREBASE_DATA_LISTENER_TAG, "target listener: id = ${targetSnapshot.key} [title]=${targetSnapshot.child(TARGET.TITLE).value} [cost] = ${targetSnapshot.child(TARGET.COST).value}")
                        list.add(TargetDTO.parseSnapshot(targetSnapshot))
                    }
                    _targets.postValue(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("REPO_TAG", "Fail load targets:")
                    Log.d("REPO_TAG", error.message)
                }

            })
    }

    /*private fun addTargetFirebase(target: TargetDTO){
        val uid = authRepository.user.value!!.uid
        val mapToAdd = mutableMapOf<String, Any>()
        val newCategory = CategoriesDto(
            id = UUID.randomUUID().toString(),
            name = target.title,
            icon = R.drawable.ic_category_placeholder,
            targetId = target.id
        )
        mapToAdd["/${FirebaseHelper.TARGETS_KEY}/${uid}/${target.id}"] = target.toMap()
        mapToAdd["/${FirebaseHelper.CATEGORIES_KEY}/${uid}/${newCategory.id}"] = newCategory.toMap()

        dbRef.updateChildren(mapToAdd)
    }*/

    private fun loadTargets(){
        targetList = mutableListOf(
            TargetDTO(
                id = "720cb4c2-46e6-48e6-8098-87625b866802",
                title = "На машину",
                cost = 700000,
                currentAmount = 0,
                date = LocalDate.of(2022, 6, 20)
            ),
            TargetDTO(
                id = "f5ced627-5fab-41ef-88d8-19599fae79ef",
                title = "На гараж",
                cost = 80000,
                currentAmount = 20000,
                date = LocalDate.of(2022, 7, 12)
            ))
    }

    fun getTargetById(id: String): TargetDTO{
        val index = _targets.value?.indexOfFirst { it.id == id } ?: throw TargetNotFoundException()
        if (index == -1 ) throw TargetNotFoundException()
        return _targets.value?.get(index)!!
    }

    fun addTarget(target: TargetDTO){
//        if (!isTargetExist(target)) {
//            targetList.add(target)
//        }
//        notifyChanges()
        val uid = authRepository.user.value!!.uid
        val mapToAdd = mutableMapOf<String, Any>()
        val newCategory = CategoriesDto(
            id = UUID.randomUUID().toString(),
            name = target.title,
            icon = R.drawable.ic_category_placeholder,
            targetId = target.id
        )
        mapToAdd["/${FirebaseHelper.TARGETS_KEY}/${uid}/${target.id}"] = target.toMap()
        mapToAdd["/${FirebaseHelper.CATEGORIES_KEY}/${uid}/${newCategory.id}"] = newCategory.toMap()

        dbRef.updateChildren(mapToAdd)
    }

    suspend fun editTarget(target: TargetDTO){
        //new Fire
        val index = _targets.value?.indexOfFirst { it.id == target.id } ?: throw TargetNotFoundException()
        if (index != -1) {
            val editedTarget = _targets.value?.get(index)?.copy(
                title = target.title,
                cost = target.cost,
                currentAmount = target.currentAmount,
                date = target.date,
                description = target.description
            )
            val uid = authRepository.user.value!!.uid
            val mapToAdd = mutableMapOf<String, Any>()


            val newCategory = categoriesService.getCategoryByTargetId(editedTarget!!.id)
                ?.copy(name = editedTarget.title)!!

            mapToAdd["/${FirebaseHelper.TARGETS_KEY}/${uid}/${target.id}"] = target.toMap()
            mapToAdd["/${FirebaseHelper.CATEGORIES_KEY}/${uid}/${newCategory.id}"] = newCategory.toMap()

            dbRef.updateChildren(mapToAdd).await()
        }
    }

    fun isTargetExist(target: TargetDTO) =
        _targets.value?.indexOfFirst { target.id == it.id } != -1
        // Old RAM
        /*targetList.indexOfFirst { target.id == it.id } != -1*/

    /*  при "синхронизации" операции с целью
        проверяет не стала ли она выполнена
        и если так, то меняем флаг, чтобы цель
        не отображалась
    */

    fun addOperationToTarget(operation: OperationsDto){
        //New Fire
        val id = operation.category.targetId
        if (id.isNotEmpty()) {
            val currTarget = getTargetById(id)
            val newTarget = currTarget.copy(currentAmount = currTarget.currentAmount + operation.amount)
            dbRefTargets.child(newTarget.id).updateChildren(newTarget.toMap())

            if (hasTargetDone(newTarget))
                setTargetCompleted(newTarget)
        }

        //old RAM
        /*val id = operation.category.targetId
        if (id.isNotEmpty()){
            val target = getTargetById(id)
            target.currentAmount += operation.amount

            if (hasTargetDone(target))
                setDoneTarget(target)
        }*/
    }

    private fun hasTargetDone(target: TargetDTO) = target.cost <= target.currentAmount

    private fun setTargetCompleted(target: TargetDTO){
        val uid = authRepository.user.value!!.uid
        target.isDone = true
        val doneTarget = target.copy(isDone = true)
        val category = categoriesService.getCategoryByTargetId(doneTarget.id)!!

        val mapToUpdate = mutableMapOf<String, Any?>()

        mapToUpdate["/${FirebaseHelper.TARGETS_KEY}/${uid}/${doneTarget.id}"] = null
        mapToUpdate["/${FirebaseHelper.COMPLETED_TARGETS_KEY}/${uid}/${doneTarget.id}"] = doneTarget.toMap()
        mapToUpdate["/${FirebaseHelper.CATEGORIES_KEY}/${uid}/${category.id}"] = null

        dbRef.updateChildren(mapToUpdate)
    }

    fun removeTarget(target: TargetDTO){
        if (!target.isDone) {
            val uid = authRepository.user.value!!.uid
            val mapToUpdate = mutableMapOf<String, Any?>()
            val category = categoriesService.getCategoryByTargetId(target.id)!!
            mapToUpdate["/${FirebaseHelper.TARGETS_KEY}/${uid}/${target.id}"] = null
            mapToUpdate["/${FirebaseHelper.CATEGORIES_KEY}/${uid}/${category.id}"] = null

            dbRef.updateChildren(mapToUpdate)
        } else
            dbRefTargetsCompleted.child(target.id).removeValue()
    }

    fun addListener(listener: TargetListener){
        listeners.add(listener)
        listener.invoke(targetList)
    }
}