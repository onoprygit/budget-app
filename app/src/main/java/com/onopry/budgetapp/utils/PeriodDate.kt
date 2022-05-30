package com.onopry.budgetapp.utils

import java.time.LocalDate

data class PeriodDate(
    val startDate: LocalDate,
    val finishDate: LocalDate,
    val periodRange: PeriodRange) {

    override fun toString() =
        when (periodRange){
            PeriodRange.MONTH -> {
                val date = startDate.getTextLocalDateMY()
                "За ${date.first} ${date.second}"
            }
            PeriodRange.YEAR -> {
                val date = startDate.getTextLocalDateMY()
                "За ${date.second} год"
            }
            PeriodRange.WEEK -> {
                val date = startDate.getTextLocalDateMY()
                "хуй знает"
            }
            PeriodRange.OTHER -> {
                val sDate = startDate.getTextLocalDateDMY()
                val fDate = finishDate.getTextLocalDateDMY()
                "${sDate.first} ${sDate.second} ${sDate.third} - ${fDate.first} ${fDate.second} ${fDate.third}"
            }
        }
}
