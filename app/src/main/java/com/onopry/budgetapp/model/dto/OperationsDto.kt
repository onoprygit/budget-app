package com.onopry.budgetapp.model.dto

import com.onopry.budgetapp.model.*
import java.io.Serializable
import java.time.LocalDate

data class OperationsDto(
    val id: String,
    var amount: Int,
    var category: CategoriesDto,
    var date: LocalDate = LocalDate.of(2022,1,1),
    var isExpence: Boolean = true,
    val accountId: String = ""
): Serializable {
    fun toMap() = mutableMapOf(
        CHILD_OPERATION_AMOUNT to amount,
        CHILD_OPERATION_CATEGORY to category.toMap(),
        CHILD_OPERATION_DATE to date.toString(),
        CHILD_OPERATION_IS_EXPENCE to isExpence,
        CHILD_OPERATION_ACCOUNT_ID to accountId
    )

    override fun toString(): String {
        return """ OPERATION:
            |id: $id
            |amount: $amount
            |category:
            |       ${category.id}
            |       ${category.name}
            |date: $date
            |isExpence: $isExpence
            |accountId: $accountId
        """.trimMargin()
    }
}




