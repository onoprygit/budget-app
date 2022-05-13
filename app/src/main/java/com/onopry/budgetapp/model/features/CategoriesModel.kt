package com.onopry.budgetapp.model.features

//sasdasd
class CategoriesModel(
    private val categoriesDataSourse: CategoriesDataSourse
) {
    fun getCategories() = categoriesDataSourse.getCategoryes()
}