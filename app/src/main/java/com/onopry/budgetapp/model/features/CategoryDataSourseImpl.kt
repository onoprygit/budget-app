package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto

class CategoryDataSourseImpl: CategoriesDataSourse {
    //https://tablericons.com/
    override fun getCategoryes() = mutableListOf(
        CategoriesDto(
            name = "Авто",
            icon = R.drawable.ic_category_car
        ),
        CategoriesDto(
            name = "Продукты",
            icon = R.drawable.ic_category_food
        ),
        CategoriesDto(
            name = "Транпорт",
            icon = R.drawable.ic_category_transport
        ),
        CategoriesDto(
            name = "Кафе",
            icon = R.drawable.ic_category_cafe
        ),
        CategoriesDto(
            name = "Одежда",
            icon = R.drawable.ic_category_clothes
        ),
        CategoriesDto(
            name = "Дом",
            icon = R.drawable.ic_category_home
        ),
        CategoriesDto(
            name = "Подарки",
            icon = R.drawable.ic_category_gift
        ),
        CategoriesDto(
            name = "Питомцы",
            icon = R.drawable.ic_category_pets
        ),
        CategoriesDto(
            name = "Развлечения",
            icon = R.drawable.ic_category_entertainment
        ),
        CategoriesDto(
            name = "Здоровье",
            icon = R.drawable.ic_category_health
        )
    )

}