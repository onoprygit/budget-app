package com.onopry.budgetapp.model.dto

import com.google.firebase.database.DataSnapshot
import com.onopry.budgetapp.utils.CATEGORY
import com.onopry.budgetapp.utils.OPERATION
import com.onopry.budgetapp.utils.TARGET
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.days

data class TargetDTO(
    val id: String,
    var title: String,
    var cost: Int,
    var currentAmount: Int,
    var date: LocalDate,
    var description: String = "",
    var isDone: Boolean = false
) {
    val restDay: Long
        get() {
            return ChronoUnit.DAYS.between(LocalDate.now(), date)
        }
    val restMoney: Int
        get() {
            return  (currentAmount / (cost.toFloat()/100)).roundToInt()
        }

    fun toMap() = mapOf<String, Any>(
        TARGET.TITLE to title,
        TARGET.CURRENT_AMOUNT to currentAmount,
        TARGET.COST to cost,
        TARGET.DATE to date.toString(),
        TARGET.DESCRIPTION to description,
        TARGET.IS_DONE to isDone
    )

    companion object {
        fun parseSnapshot(snapshot: DataSnapshot) =
            TargetDTO(
                id = snapshot.key as String,
                title = snapshot.child(TARGET.TITLE).value as String,
                cost = (snapshot.child(TARGET.COST).value as Long).toInt(),
                currentAmount = (snapshot.child(TARGET.CURRENT_AMOUNT).value as Long).toInt(),
                date = LocalDate.parse((snapshot.child(TARGET.DATE).value as String)),
                description = snapshot.child(TARGET.DESCRIPTION).value as String,
                isDone = snapshot.child(TARGET.IS_DONE).value as Boolean,
            )

        fun errorTarget() = TargetDTO(
            id = "",
            title = "Error",
            cost = 0,
            currentAmount = -99,
            date = LocalDate.now()
        )
    }
}