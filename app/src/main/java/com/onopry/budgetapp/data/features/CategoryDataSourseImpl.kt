package com.onopry.budgetapp.data.features

import com.onopry.budgetapp.R
import com.onopry.budgetapp.data.dto.CategoriesDto

class CategoryDataSourseImpl: CategoriesDataSourse {
    override fun getCategoryes() = mutableListOf(
        CategoriesDto(
            category = "Авто",
            iconSec = R.drawable.ic_ctegory_car
        ),
        CategoriesDto(
            category = "Продукты",
            iconSec = R.drawable.ic_category_eat
        ),
        CategoriesDto(
            category = "Транпорт",
            iconSec = R.drawable.ic_category_bus
        ),
        CategoriesDto(
            category = "Кафе",
            iconSec = R.drawable.ic_category_cafe
        ),
        CategoriesDto(
            category = "Одежда",
            iconSec = R.drawable.ic_category_clothes
        ),
        CategoriesDto(
            category = "Дом",
            iconSec = R.drawable.ic_category_house
        ),
        CategoriesDto(
            category = "Подарки",
            iconSec = R.drawable.ic_category_gift
        ),
        CategoriesDto(
            category = "Питомцы",
            iconSec = R.drawable.ic_category_pets
        ),
        CategoriesDto(
            category = "Развлечения",
            iconSec = R.drawable.ic_category_fun
        ),
        CategoriesDto(
            category = "Здоровье",
            iconSec = R.drawable.ic_category_health
        )
    )

}