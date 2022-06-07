package com.onopry.budgetapp.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.utils.FIREBASE

class CategoryRepository(
    private val categoriesService: CategoriesService
    ) {

    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefRoot = dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!)

    private val _categories = MutableLiveData<List<CategoriesDto>>()
    val categories: LiveData<List<CategoriesDto>> = _categories

    init {
        load()
    }

    private fun load(){
        dbRefRoot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoriesDto>()
                snapshot.children.mapNotNull {
                    list.add(CategoriesDto.parseSnapshot(it))
                }
                _categories.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("REPO_TAG", "Fail load categories")
            }

        })
    }

    companion object{
        suspend fun generateDefaultUserCategories(refCategories: DatabaseReference, categoriesService: CategoriesService){
            val defCategoriesList = categoriesService.getCategoriesList()
            val map = HashMap<String, Any>()
            defCategoriesList.forEach { category ->
                map["/${category.id}"] = category.toMap()
            }
            refCategories.updateChildren(map)
        }
    }

}
