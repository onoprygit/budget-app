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
    var categories: Pair<CategoriesDto, CategoriesDto>,
    val accountId: String = "",
    val description: String = "",
): Serializable {
    fun toMap() = mapOf<String, Any>(
        OPERATION.AMOUNT to amount,
        OPERATION.CATEGORIES to categories.toMap(),
        OPERATION.DATE to date.toString(),
        OPERATION.IS_EXPENCE to isExpence,
        OPERATION.ACCOUNT_ID to accountId,
        OPERATION.DESCRIPTION to description
    )

    companion object {
        fun parseSnapshot(snapshot: DataSnapshot) =
            OperationsDto(
                id = snapshot.key as String,
                amount = (snapshot.child(OPERATION.AMOUNT).value as Long).toInt(),
                date = LocalDate.parse((snapshot.child(OPERATION.DATE).value as String)),
                isExpence = snapshot.child(CATEGORY.IS_EXPENCE).value as Boolean,
                categories = CategoriesDto.parseSnapshotPair(snapshot.child(OPERATION.CATEGORIES)),
                accountId = snapshot.child(OPERATION.ACCOUNT_ID).value as String,
                description = snapshot.child(OPERATION.DESCRIPTION).value as String,
            )

    }
}






