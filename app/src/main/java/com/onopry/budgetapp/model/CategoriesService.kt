package com.onopry.budgetapp.model
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.utils.CategoryNotFoundException
import com.onopry.budgetapp.utils.MY_COLORS
import com.onopry.budgetapp.utils.OperationNotFoundException
import java.util.*

// Листенер отдает список категорий, который будет обновлен после какой либо операций
typealias CategoriesListener = (categories: List<CategoriesDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает

class CategoriesService {

    private var categoriesList = mutableListOf<CategoriesDto>()
    private val listeners = mutableSetOf<CategoriesListener>()

    init {
        loadCategories()
        }

    private fun loadCategories(){
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
            )
        )
    }

    //hardcode hell
    private fun getCategoriesColors(): Stack<Int>{
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

    fun getExpencesCategories() = categoriesList.filter { it.isExpence }

    fun getIncomesCategories() = categoriesList.filter { !it.isExpence }


    fun addListener(listener: CategoriesListener){
        listeners.add(listener)
        listener.invoke(categoriesList)
    }

    fun removeListener(listener: CategoriesListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(categoriesList) } }

}