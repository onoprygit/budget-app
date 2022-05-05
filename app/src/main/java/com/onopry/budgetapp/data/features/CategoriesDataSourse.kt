package com.onopry.budgetapp.data.features

import com.onopry.budgetapp.data.dto.CategoriesDto

interface CategoriesDataSourse {
    fun getCategoryes(): MutableList<CategoriesDto>
}