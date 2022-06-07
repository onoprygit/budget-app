package com.onopry.budgetapp.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.FIREBASE
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

class OperationsRepository {
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefRoot = dbRef.child(FirebaseHelper.OPERATIONS_KEY).child(uid!!)

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    init {
        fetch()
    }

    private fun fetch(){
        dbRefRoot.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<OperationsDto>()
                snapshot.children.mapNotNull {
                    list.add(OperationsDto.parseSnapshot(it))
                }
                _operations.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("REPO_TAG", "Fail load operations")
            }

        })
    }

    companion object{
        suspend fun generateDefaultUserOperations(refOperations: DatabaseReference){
            val defOperationsList = operations()
            val map = HashMap<String,Any>()
            defOperationsList.forEach { operation ->
                map["/${operation.id}"] = operation.toMap()
            }
            refOperations.updateChildren(map)
        }

        private fun operations(): List<OperationsDto> {
            val list = (1..30).map {
                val defCategoriesList = CategoriesModel(CategoryDataSourseImpl()).getCategories()
                OperationsDto(
                    id = UUID.randomUUID().toString(),
                    amount = Random.nextInt(100, 10000),
                    categoryId = defCategoriesList[Random.nextInt(0, 9)].id,
                    date = LocalDate.of(2022, Random.nextInt(4, 7), Random.nextInt(8, 25)),
                    isExpence = Random.nextBoolean()
                )
            }.toMutableList()
            list.sortByDescending { it.date }
            return list
        }
    }
}