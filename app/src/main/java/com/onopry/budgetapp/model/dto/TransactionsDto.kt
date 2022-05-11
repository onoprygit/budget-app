package com.onopry.budgetapp.model.dto

import com.onopry.budgetapp.model.dto.CategoriesDto

data class TransactionsDto(
    val amount: Int,
    val category: CategoriesDto
//  TODO: Добавить дату, время
)
