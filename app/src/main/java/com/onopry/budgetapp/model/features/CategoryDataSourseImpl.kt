package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto

class CategoryDataSourseImpl: CategoriesDataSourse {
    override fun getCategoryes() = mutableListOf(
        CategoriesDto(
            category = "Авто",
            icon = R.drawable.ic_ctegory_car
        ),
        CategoriesDto(
            category = "Продукты",
            icon = R.drawable.ic_category_eat
        ),
        CategoriesDto(
            category = "Транпорт",
            icon = R.drawable.ic_category_bus
        ),
        CategoriesDto(
            category = "Кафе",
            icon = R.drawable.ic_category_cafe
        ),
        CategoriesDto(
            category = "Одежда",
            icon = R.drawable.ic_category_clothes
        ),
        CategoriesDto(
            category = "Дом",
            icon = R.drawable.ic_category_house
        ),
        CategoriesDto(
            category = "Подарки",
            icon = R.drawable.ic_category_gift
        ),
        CategoriesDto(
            category = "Питомцы",
            icon = R.drawable.ic_category_pets
        ),
        CategoriesDto(
            category = "Развлечения",
            icon = R.drawable.ic_category_fun
        ),
        CategoriesDto(
            category = "Здоровье",
            icon = R.drawable.ic_category_health
        )
    )

}