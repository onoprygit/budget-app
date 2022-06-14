package com.onopry.budgetapp.model.services
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.repo.FirebaseHelper
import com.onopry.budgetapp.utils.FIREBASE
import com.onopry.budgetapp.utils.LogTags
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

// Листенер отдает список категорий, который будет обновлен после какой либо операций
typealias CategoriesListener = (categories: List<CategoriesDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает


class CategoriesService @Inject constructor(
    private val authRepository: AuthRepository
) {

    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference

    private val _categories = MutableLiveData<List<CategoriesDto>>()
    val categories:LiveData<List<CategoriesDto>> = _categories

    private val _parentCategories = MutableLiveData<List<CategoriesDto>>()
    val parentCategories:LiveData<List<CategoriesDto>> = _parentCategories

    private val _childCategories = MutableLiveData<List<CategoriesDto>>()
    val childCategories:LiveData<List<CategoriesDto>> = _childCategories

    init {
        Log.d(LogTags.DI_INSTANCES_TAG, "CategoriesService init")
        loadCategories()
        }

    private fun loadCategories(){
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<CategoriesDto>()
                    snapshot.children.mapNotNull {
                        list.add(CategoriesDto.parseSnapshot(it))
                    }
                    _categories.postValue(list)
                }

            override fun onCancelled(error: DatabaseError) {
                Log.d("REPO_TAG", "Fail load categories:")
                Log.d("REPO_TAG", error.message)
            }

        })
        dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid)
    }


    suspend fun generateDefaultUserCategoriesAsync() = coroutineScope {
        async {
            val uid = authRepository.user.value!!.uid
            val returnedCategoryList = mutableListOf<CategoriesDto>()
            val defCategoriesList = CategoriesModel(CategoryDataSourseImpl()).getCategories()
            Log.d("GENERATE_DATA_TAG", "generateDefaultUserCategories: ${defCategoriesList.size}")
            val map = HashMap<String, Any>()
            defCategoriesList.forEach { category ->
                map["/${category.id}"] = category.toMap()
                returnedCategoryList.add(category)
            }
            dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid).updateChildren(map)
            returnedCategoryList
        }
    }

    fun getCategoryByTargetId(id: String) = _categories.value?.find { it.targetId == id }

    fun getParentCategoryByParentId(parentId: String) = _categories.value?.find { it.id == parentId && it.isParent }
}