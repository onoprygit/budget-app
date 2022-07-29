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
import com.onopry.budgetapp.model.dto.TargetDto
import com.onopry.budgetapp.model.dto.getChildCategory
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.utils.*
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

typealias TargetListener = (target: List<TargetDto>) -> Unit

class TargetService @Inject constructor(
    private val authRepository: AuthRepository,
    private val categoriesService: CategoriesService
) {
    private var targetList = mutableListOf<TargetDto>()
    private val listeners = mutableSetOf<TargetListener>()

    private val _targets = MutableLiveData<List<TargetDto>>()
    val targets: LiveData<List<TargetDto>> = _targets

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefTargets = dbRef.child(TARGET.NODE).child(authRepository.user.value!!.uid)
    private val dbRefTargetsCompleted = dbRef.child(TARGET.COMPLETED_NODE)

    init {
        //loadTargets()
        loadFirebase()
    }

    private fun loadFirebase(){
        val uid = authRepository.user.value?.uid
        dbRef.child(TARGET.NODE).child(uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<TargetDto>()
                    snapshot.children.mapNotNull { targetSnapshot ->
                        Log.d(LogTags.FIREBASE_DATA_LISTENER_TAG, "target listener: id = ${targetSnapshot.key} [title]=${targetSnapshot.child(TARGET.TITLE).value} [cost] = ${targetSnapshot.child(TARGET.COST).value}")
                        list.add(TargetDto.parseSnapshot(targetSnapshot))
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
        mapToAdd["/${TARGET.NODE}/${uid}/${target.id}"] = target.toMap()
        mapToAdd["/${FirebaseHelper.CATEGORIES_KEY}/${uid}/${newCategory.id}"] = newCategory.toMap()

        dbRef.updateChildren(mapToAdd)
    }*/

    private fun loadTargets(){
        targetList = mutableListOf(
            TargetDto(
                id = "720cb4c2-46e6-48e6-8098-87625b866802",
                title = "На машину",
                cost = 700000,
                currentAmount = 0,
                date = LocalDate.of(2022, 6, 20)
            ),
            TargetDto(
                id = "f5ced627-5fab-41ef-88d8-19599fae79ef",
                title = "На гараж",
                cost = 80000,
                currentAmount = 20000,
                date = LocalDate.of(2022, 7, 12)
            ))
    }

    fun getTargetById(id: String): TargetDto{
        val index = _targets.value?.indexOfFirst { it.id == id } ?: throw TargetNotFoundException()
        if (index == -1 ) throw TargetNotFoundException()
        return _targets.value?.get(index)!!
    }

    fun addTarget(target: TargetDto){
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
        mapToAdd["/${TARGET.NODE}/${uid}/${target.id}"] = target.toMap()
        mapToAdd["/${CATEGORY.NODE}/${uid}/${newCategory.id}"] = newCategory.toMap()

        dbRef.updateChildren(mapToAdd)
    }

    suspend fun editTarget(target: TargetDto){
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

            mapToAdd["/${TARGET.NODE}/${uid}/${target.id}"] = target.toMap()
            mapToAdd["/${CATEGORY.NODE}/${uid}/${newCategory.id}"] = newCategory.toMap()

            dbRef.updateChildren(mapToAdd).await()
        }
    }

    fun isTargetExist(target: TargetDto) =
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
        val id = operation.categories.getChildCategory().targetId
        if (id.isNotEmpty()) {
            val currTarget = getTargetById(id)
            val newTarget = currTarget.copy(currentAmount = currTarget.currentAmount + operation.amount)
            dbRefTargets.child(newTarget.id).updateChildren(newTarget.toMap())

            if (hasTargetDone(newTarget))
                setTargetCompleted(newTarget)
        }

    }

    private fun hasTargetDone(target: TargetDto) = target.cost <= target.currentAmount

    private fun setTargetCompleted(target: TargetDto){
        val uid = authRepository.user.value!!.uid
        target.isDone = true
        val doneTarget = target.copy(isDone = true)
        val category = categoriesService.getCategoryByTargetId(doneTarget.id)!!

        val mapToUpdate = mutableMapOf<String, Any?>()

        mapToUpdate["/${TARGET.NODE}/${uid}/${doneTarget.id}"] = null
        mapToUpdate["/${TARGET.COMPLETED_NODE}/${uid}/${doneTarget.id}"] = doneTarget.toMap()
        mapToUpdate["/${CATEGORY.NODE}/${uid}/${category.id}"] = null

        dbRef.updateChildren(mapToUpdate)
    }

    fun removeTarget(target: TargetDto){
        if (!target.isDone) {
            val uid = authRepository.user.value!!.uid
            val mapToUpdate = mutableMapOf<String, Any?>()
            val category = categoriesService.getCategoryByTargetId(target.id)!!
            mapToUpdate["/${TARGET.NODE}/${uid}/${target.id}"] = null
            mapToUpdate["/${CATEGORY.NODE}/${uid}/${category.id}"] = null

            dbRef.updateChildren(mapToUpdate)
        } else
            dbRefTargetsCompleted.child(target.id).removeValue()
    }

    fun addListener(listener: TargetListener){
        listeners.add(listener)
        listener.invoke(targetList)
    }
}