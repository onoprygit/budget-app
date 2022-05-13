package com.onopry.budgetapp.model.dto

import com.onopry.budgetapp.model.dto.CategoriesDto
import java.util.*

data class TransactionsDto(
    val id: UUID,
    var amount: Int,
    var category: CategoriesDto
//  TODO: Добавить дату, время
)
