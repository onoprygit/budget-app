package com.onopry.budgetapp.model.services
import android.graphics.Color
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.utils.*
import java.util.*

// Листенер отдает список категорий, который будет обновлен после какой либо операций
typealias CategoriesListener = (categories: List<CategoriesDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает

class CategoriesService {

    private var categoriesList = mutableListOf<CategoriesDto>()
    private val listeners = mutableSetOf<CategoriesListener>()
    private val targetService = TargetService()

    @Deprecated("Для локалки, потом убрать")
    private fun loadCategoriesLocal(){
        val categoryColorsStack = getCategoriesColors()
        categoriesList = mutableListOf(
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
                name = "Транпорт",
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
                name = "Подарки",
                icon = R.drawable.ic_category_gift,
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
                name = "Развлечения",
                icon = R.drawable.ic_category_entertainment,
                color = categoryColorsStack.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Здоровье",
                icon = R.drawable.ic_category_health
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "На машинк",
                icon = R.drawable.ic_category_placeholder,
                color = Color.parseColor("#809A6F"),
                targetId = "720cb4c2-46e6-48e6-8098-87625b866802",
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "На гараж",
                icon = R.drawable.ic_category_placeholder,
                color = Color.parseColor("#809A6F"),
                targetId = "f5ced627-5fab-41ef-88d8-19599fae79ef",
            )
        )
    }

    suspend fun loadCategories(){
        Log.d("HUITAAAA", "CATEGORIES_SERVICE load start")
//        val TAG = "LOAD_CATEGORY_TEST"
        Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged ${categoriesList.size}")
        REF_DB_ROOT
            .child(NODE_CATEGORIES)
            .child(AUTH.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<CategoriesDto>()
                    Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged start")

                    snapshot.children.forEach {
                        list.add(
                            CategoriesDto(
                                id = it.key as String,
                                name = it.child(CHILD_CATEGORY_NAME).value as String,
                                icon = (it.child(CHILD_CATEGORY_ICON).value as Long).toInt(),
                                color = (it.child(CHILD_CATEGORY_COLOR).value as Long).toInt(),
                                isParent = it.child(CHILD_CATEGORY_IS_PARENT).value as Boolean,
                                isExpence = it.child(CHILD_CATEGORY_IS_EXPENCE).value as Boolean,
                                parentId = it.child(CHILD_CATEGORY_PARENT_ID).value as String,
                                targetId = it.child(CHILD_CATEGORY_TARGET_ID).value as String,
                        ))
                    }
                    Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged end")
                    categoriesList = list
                    Log.d("HUITAAAA", "CATEGORIES_SERVICE onDataChanged ${categoriesList.size}")
//                    Log.d(TAG, "$list.toString()")
                    notifyChanges()
                }

                override fun onCancelled(error: DatabaseError) {
                    throw CategoriesUploadCancelledException()
                }

            })
        Log.d("HUITAAAA", "CATEGORIES_SERVICE load end")
    }

    suspend fun getCategoryById_(){

    }

    fun category_log(){
        Log.d("CATEGORY_LOG_TAG", "size: ${categoriesList.size}, list: $categoriesList")
    }

    //hardcode hell
    private fun getCategoriesColors(): Stack<Int>{
        val colorsStack: Stack<Int> = Stack()
        colorsStack.push(MY_COLORS.color_category_1)
        colorsStack.push(MY_COLORS.color_category_2)
//        colorsStack.push(MY_COLORS.color_category_3)
        colorsStack.push(MY_COLORS.color_category_4)
        colorsStack.push(MY_COLORS.color_category_5)
        colorsStack.push(MY_COLORS.color_category_6)
        colorsStack.push(MY_COLORS.color_category_7)
        colorsStack.push(MY_COLORS.color_category_8)
        colorsStack.push(MY_COLORS.color_category_9)
        colorsStack.push(MY_COLORS.color_category_10)
//        colorsStack.push(MY_COLORS.color_category_11)
        colorsStack.push(MY_COLORS.color_category_12)
        colorsStack.push(MY_COLORS.color_category_13)
        colorsStack.push(MY_COLORS.color_category_14)
        colorsStack.push(MY_COLORS.color_category_15)
        colorsStack.shuffle()
        return colorsStack
    }

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

    fun updateCategory(category: CategoriesDto) {
        val uid = AUTH.currentUser?.uid.toString()
        REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).child(category.id).updateChildren(category.toMap())
    }

    fun getExpencesCategories() = categoriesList.filter { it.isExpence }

    fun getIncomesCategories() = categoriesList.filter { !it.isExpence }


    fun addListener(listener: CategoriesListener){
        listeners.add(listener)
        listener.invoke(categoriesList)
    }

    fun removeListener(listener: CategoriesListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(categoriesList) } }

}