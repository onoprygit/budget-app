package com.onopry.budgetapp.model.dto

import android.os.Parcelable
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.utils.*
import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class OperationsDto(
    val id: String,
    var amount: Int,
    var category: CategoriesDto,
    var date: LocalDate = LocalDate.of(2022,1,1),
    var isExpence: Boolean = true,
    val accountId: String? = null
): Serializable {
    fun toMap() = mutableMapOf<String, Any>(
        CHILD_ID to id,
        CHILD_AMOUNT to amount,
        CHILD_CATEGORY to category,
        CHILD_DATE to date.toString(),
        CHILD_IS_EXPENCE to isExpence
    )
}




