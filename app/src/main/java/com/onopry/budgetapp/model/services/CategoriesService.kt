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
import com.onopry.budgetapp.utils.CategoryNotFoundException
import com.onopry.budgetapp.utils.FIREBASE
import com.onopry.budgetapp.utils.LogTags
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

// Листенер отдает список категорий, который будет обновлен после какой либо операций
typealias CategoriesListener = (categories: List<CategoriesDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает


class CategoriesService @Inject constructor(
    private val authRepository: AuthRepository
) {

    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
//    private val refCateories = dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!)

    private var categoriesList = mutableListOf<CategoriesDto>()

    private val _categories = MutableLiveData<List<CategoriesDto>>()
    val categories:LiveData<List<CategoriesDto>> = _categories


    private val listeners = mutableSetOf<CategoriesListener>()
//    private val targetService = TargetService()



    init {
        Log.d(LogTags.DI_INSTANCES_TAG, "CategoriesService init")
        loadCategoriesLocal()
        load()
        }

    private fun load(){
//        authRepository.user.value.uid
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!)
            .addValueEventListener(object : ValueEventListener {
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

    suspend fun generateDefaultUserCategoriesAsync() = coroutineScope {
        async {
            val uid = authRepository.user.value!!.uid
            val returnedCategoryList = mutableListOf<CategoriesDto>()
//        val uid = FirebaseAuth.getInstance().currentUser?.uid
            val defCategoriesList = CategoriesModel(CategoryDataSourseImpl()).getCategories()
            Log.d("GENERATE_DATA_TAG", "generateDefaultUserCategories: ${defCategoriesList.size}")
            val map = HashMap<String, Any>()
            defCategoriesList.forEach { category ->
                map["/${category.id}"] = category.toMap()
                returnedCategoryList.add(category)
            }
            dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!).updateChildren(map)
            returnedCategoryList
        }
    }

    suspend fun loadSingleCategories() = mutableListOf<CategoriesDto>().apply {
        val uid = authRepository.user.value!!.uid
        val ds = dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!).get().await()
    }

    //Firebase end

    fun getCategoriesList() = categoriesList.toList()

    fun getCategoriesListSize() = categoriesList.size

    fun getCategoryById(id: String) =
        categoriesList.firstOrNull { it.id == id } ?: throw CategoryNotFoundException()

    fun getParentExpencesCategories() =
        categoriesList.filter { it.isParent && it.isExpence }

    fun getParentIncomeCategories() =
        categoriesList.filter { it.isParent && !it.isExpence }

    fun getChildCategoriesByParentId(parentId: String) =
        categoriesList.filter { !it.isParent && it.parentId == parentId }

    // todo: Мб сделать оду функцию, возвращающую пару Трат и Доходов, а не по одтельности
    fun getExpencesCategoriesAsMap(): MutableMap<String, List<CategoriesDto>> {
        val categoriesMap = mutableMapOf<String, List<CategoriesDto>>()
        getParentExpencesCategories()
            .forEach { parentCategory ->
            categoriesMap[parentCategory.id] = getChildCategoriesByParentId(parentCategory.id)
        }
        return categoriesMap
    }

    fun getIncomesCategoriesAsMap(): MutableMap<String, List<CategoriesDto>> {
        val categoriesMap = mutableMapOf<String, List<CategoriesDto>>()
        getParentIncomeCategories()
            .forEach { parentCategory ->
                categoriesMap[parentCategory.id] = getChildCategoriesByParentId(parentCategory.id)
            }
        return categoriesMap
    }

    fun addCategory(category: CategoriesDto){
        categoriesList.add(category)
        notifyChanges()
    }

    private fun notifyChanges(){ listeners.forEach { it.invoke(categoriesList) } }

    fun getCategoryByTargetId(id: String) = _categories.value?.find { it.targetId == id }

    //hardcode hell
/*    private fun getCategoriesColorsLocal(): Stack<Int>{
        val colorsStack: Stack<Int> = Stack()
        colorsStack.push(MY_COLORS.color_category_1)
        colorsStack.push(MY_COLORS.color_category_2)
        colorsStack.push(MY_COLORS.color_category_3)
        colorsStack.push(MY_COLORS.color_category_4)
        colorsStack.push(MY_COLORS.color_category_5)
        colorsStack.push(MY_COLORS.color_category_6)
        colorsStack.push(MY_COLORS.color_category_7)
        colorsStack.push(MY_COLORS.color_category_8)
        colorsStack.push(MY_COLORS.color_category_9)
        colorsStack.push(MY_COLORS.color_category_10)
        colorsStack.push(MY_COLORS.color_category_11)
        colorsStack.push(MY_COLORS.color_category_12)
        colorsStack.push(MY_COLORS.color_category_13)
        colorsStack.push(MY_COLORS.color_category_14)
        colorsStack.push(MY_COLORS.color_category_15)
        return colorsStack
    }

    private fun loadCategoriesLocal(){
        val categoryColorsStack = getCategoriesColorsLocal()
         val categories = mutableListOf(
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Авто",
                icon = R.drawable.ic_category_car,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Продукты",
                icon = R.drawable.ic_category_food,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Транспорт",
                icon = R.drawable.ic_category_transport,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Кафе",
                icon = R.drawable.ic_category_cafe,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Одежда",
                icon = R.drawable.ic_category_clothes,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Дом",
                icon = R.drawable.ic_category_home,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Питомцы",
                icon = R.drawable.ic_category_pets,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Хобби",
                icon = R.drawable.ic_category_entertainment,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Здоровье",
                icon = R.drawable.ic_category_health
            )
        )

        categoriesList = categories
    }*/

    private fun loadCategoriesLocal() = CategoriesModel(CategoryDataSourseImpl()).getCategories()
}