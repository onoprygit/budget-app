package com.onopry.budgetapp.model

import com.onopry.budgetapp.model.dto.CategoriesDto

data class Transactions(
    val amount: Int,
    val category: CategoriesDto
)
