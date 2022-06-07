package com.onopry.budgetapp.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.repo.FirebaseHelper.CATEGORIES_KEY
import com.onopry.budgetapp.model.repo.FirebaseHelper.TARGETS_KEY
import com.onopry.budgetapp.utils.FIREBASE
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

class TargetRepository {
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefRoot = dbRef.child(FirebaseHelper.TARGETS_KEY).child(uid!!)

    private val _targets = MutableLiveData<List<TargetDTO>>()
    val targets: LiveData<List<TargetDTO>> = _targets

    init {
        //fetch()
    }

    private fun fetch(){
        dbRefRoot.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<TargetDTO>()
                snapshot.children.mapNotNull {
                    list.add(TargetDTO.parseSnapshot(it))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("REPO_TAG", "Fail load targets")
            }

        })
    }

    companion object {
        suspend fun generateDefaultUserTargets(reference: DatabaseReference, uid: String){
            val defTargetsList = targets()
            val tMap = HashMap<String, Any>()
            val cMap = HashMap<String, Any>()
            defTargetsList.forEach { targets ->
                val catId = UUID.randomUUID().toString()
                tMap["/${TARGETS_KEY}/$uid/${targets.id}"] = targets.toMap()
                tMap["/${CATEGORIES_KEY}/$uid/${catId}"] = CategoriesDto(
                    id = catId,
                    name = targets.title,
                    targetId = targets.id
                )
            }
            reference.updateChildren(tMap)

        }

        private fun targets() =
            listOf(
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
}