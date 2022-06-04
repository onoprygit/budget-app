package com.onopry.budgetapp.model.dto

import com.onopry.budgetapp.model.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

data class TargetDTO(
    val id: String,
    var title: String,
    var cost: Int,
    var currentAmount: Int,
    var date: LocalDate,
    var description: String? = null,
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

    fun toMap() = mutableMapOf<String, Any>(
//        CHILD_TARGET_ID to id,
        CHILD_TARGET_TITLE to title,
        CHILD_TARGET_COST to cost,
        CHILD_TARGET_CURRENT_AMOUNT to currentAmount,
        CHILD_TARGET_DATE to date.toString(),
        CHILD_TARGET_DESRIPTION to "def",
        CHILD_TARGET_IS_DONE to isDone
    )
}