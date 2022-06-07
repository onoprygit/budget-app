package com.onopry.budgetapp.model.dto

import com.google.firebase.database.DataSnapshot
import com.onopry.budgetapp.utils.*
import java.io.Serializable
import java.time.LocalDate

data class OperationsDto(
    val id: String,
    var amount: Int,
//    val category: CategoriesDto,
    var date: LocalDate = LocalDate.of(2022,1,1),
    var isExpence: Boolean = true,
    var categoryId: String,
    val accountId: String = ""
): Serializable {
    fun toMap() = mapOf<String, Any>(
        OPERATION.AMOUNT to amount,
        OPERATION.CATEGORY_ID to categoryId,
        OPERATION.DATE to date.toString(),
        OPERATION.IS_EXPENCE to isExpence,
        OPERATION.ACCOUNT_ID to accountId
    )

    companion object {
        fun parseSnapshot(snapshot: DataSnapshot) =
            OperationsDto(
                id = snapshot.key as String,
                amount = (snapshot.child(OPERATION.AMOUNT).value as Long).toInt(),
//                category =
                date = LocalDate.parse((snapshot.child(OPERATION.DATE).value as String)),
                isExpence = snapshot.child(CATEGORY.IS_EXPENCE).value as Boolean,
                accountId = snapshot.child(CATEGORY.PARENT_ID).value as String,
                categoryId = snapshot.child(OPERATION.CATEGORY_ID).value as String,
            )
    }
}




