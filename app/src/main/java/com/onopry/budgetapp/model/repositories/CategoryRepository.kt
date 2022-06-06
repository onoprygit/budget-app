package com.onopry.budgetapp.model.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.services.*
import com.onopry.budgetapp.utils.CategoriesUploadCancelledException

class CategoryRepository {

    private val CategoryList = mutableListOf<CategoriesDto>()

//    suspend fun loadCategories(){
//        Log.d("HUITAAAA", "CATEGORIES_SERVICE load start")
////        val TAG = "LOAD_CATEGORY_TEST"
//        REF_DB_ROOT
//            .child(NODE_CATEGORIES)
//            .child(AUTH.currentUser?.uid.toString())
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    val list = mutableListOf<CategoriesDto>()
//                    Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged start")
//
//                    snapshot.children.forEach {
//                        list.add(
//                            CategoriesDto(
//                                id = it.key as String,
//                                name = it.child(CHILD_CATEGORY_NAME).value as String,
//                                icon = (it.child(CHILD_CATEGORY_ICON).value as Long).toInt(),
//                                color = (it.child(CHILD_CATEGORY_COLOR).value as Long).toInt(),
//                                isParent = it.child(CHILD_CATEGORY_IS_PARENT).value as Boolean,
//                                isExpence = it.child(CHILD_CATEGORY_IS_EXPENCE).value as Boolean,
//                                parentId = it.child(CHILD_CATEGORY_PARENT_ID).value as String,
//                                targetId = it.child(CHILD_CATEGORY_TARGET_ID).value as String,
//                            )
//                        )
//                    }
//                    Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged end")
//                    categoriesList = list
//                    Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged ${categoriesList.size}")
////                    Log.d(TAG, "$list.toString()")
//                    notifyChanges()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    throw CategoriesUploadCancelledException()
//                }
//
//            })
//        Log.d("HUITAAAA", "CATEGORIES_SERVICE load end")
//    }

}