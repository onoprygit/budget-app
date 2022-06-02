package com.onopry.budgetapp.model.dto

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
}