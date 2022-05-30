package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.utils.CONSTANTS
import com.onopry.budgetapp.utils.MY_COLORS
import java.util.*

class CategoryDataSourseImpl: CategoriesDataSourse {
    //https://tablericons.com/
    override fun getCategoryes(): MutableList<CategoriesDto> {
        val a:Stack<Int> = Stack()
        a.push(MY_COLORS.color_category_1)
        a.push(MY_COLORS.color_category_2)
//        a.push(MY_COLORS.color_category_3)
        a.push(MY_COLORS.color_category_4)
        a.push(MY_COLORS.color_category_5)
        a.push(MY_COLORS.color_category_6)
        a.push(MY_COLORS.color_category_7)
        a.push(MY_COLORS.color_category_8)
        a.push(MY_COLORS.color_category_9)
        a.push(MY_COLORS.color_category_10)
//        a.push(MY_COLORS.color_category_11)
        a.push(MY_COLORS.color_category_12)
        a.push(MY_COLORS.color_category_13)
        a.push(MY_COLORS.color_category_14)
        a.push(MY_COLORS.color_category_15)
        a.shuffle()

        val categories = mutableListOf(
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Авто",
                icon = R.drawable.ic_category_car,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Продукты",
                icon = R.drawable.ic_category_food,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Транпорт",
                icon = R.drawable.ic_category_transport,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Кафе",
                icon = R.drawable.ic_category_cafe,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Одежда",
                icon = R.drawable.ic_category_clothes,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Дом",
                icon = R.drawable.ic_category_home,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Подарки",
                icon = R.drawable.ic_category_gift,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Питомцы",
                icon = R.drawable.ic_category_pets,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Развлечения",
                icon = R.drawable.ic_category_entertainment,
                color = a.pop()
            ),
            CategoriesDto(
                id = UUID.randomUUID().toString(),
                name = "Здоровье",
                icon = R.drawable.ic_category_health
            )
        )
        return categories
    }
}