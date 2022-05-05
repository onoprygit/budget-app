package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.dto.CategoriesDto

interface CategoriesDataSourse {
    fun getCategoryes(): MutableList<CategoriesDto>
}