package com.onopry.budgetapp.data.features

//sasdasd
class CategoriesModel(
    private val categoriesDataSourse: CategoriesDataSourse
) {
    fun getCategories() = categoriesDataSourse.getCategoryes()
}