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

typealias CategoryRepoListener = (categories: List<CategoriesDto> ) -> Unit

class CategoryRepository(
    private val categoriesService: CategoriesService
    ) {

    private val uid = FirebaseAuth.getInstance().currentUser?.uid
//    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefRoot = dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!)

    private val listeners = mutableSetOf<CategoryRepoListener>()

    private var categories = mutableListOf<CategoriesDto>()

    private val _categories = MutableLiveData<List<CategoriesDto>>()
    val categoriesLiveData: LiveData<List<CategoriesDto>> = _categories

    private fun load(){
        dbRefRoot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoriesDto>()
                snapshot.children.mapNotNull {
                    list.add(CategoriesDto.parseSnapshot(it))
                }
                _categories.postValue(list)
                notifyChanges()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("REPO_TAG", "Fail load categories:")
                Log.d("REPO_TAG", error.message)
            }

        })
    }

    fun addListener(listener: CategoryRepoListener){
        listeners.add(listener)
    }

    fun removeListener(listener: CategoryRepoListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{ it.invoke(categories) }
    }

    companion object{
        suspend fun generateDefaultUserCategories(refCategories: DatabaseReference){
//            val defCategoriesList = categoriesService.getCategoriesList()
            val defCategoriesList = CategoriesModel(CategoryDataSourseImpl()).getCategories()
            Log.d("GENERATE_DATA_TAG", "generateDefaultUserCategories: ${defCategoriesList.size}")
            val map = HashMap<String, Any>()
            defCategoriesList.forEach { category ->
                map["/${category.id}"] = category.toMap()
            }
            refCategories.updateChildren(map)
        }
    }

}
