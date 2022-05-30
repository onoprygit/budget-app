package com.onopry.budgetapp.utils

import com.onopry.budgetapp.model.dto.CategoriesDto

data class AmountByCategory(
    val category: CategoriesDto,
    val amount: Int,
)