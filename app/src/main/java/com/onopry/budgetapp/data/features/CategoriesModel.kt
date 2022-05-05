package com.onopry.budgetapp.data.features

class CategoriesModel(
    private val categoriesDataSourse: CategoriesDataSourse
) {
    fun getCategories() = categoriesDataSourse.getCategoryes()
}