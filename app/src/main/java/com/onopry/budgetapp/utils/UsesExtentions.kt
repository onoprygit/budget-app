package com.onopry.budgetapp.utils

import java.time.LocalDate

/** extension func returns day, month, year
 * @param first day
 * @param second month
 * @param third year
 **/
fun LocalDate.getTextLocalDateTriple(): Triple<String, String, String>{
    val d = dayOfMonth.toString()
    val m = when(month.value){
        1 -> "Января"
        2 -> "Февраля"
        3 -> "Марта"
        4 -> "Апреля"
        5 -> "Мая"
        6 -> "Июня"
        7 -> "Июля"
        8 -> "Августа"
        9 -> "Сентября"
        10 -> "Октября"
        11 -> "Ноября"
        12 -> "Декабря"
        else -> "Косяк"
    }
    val y = year.toString()
    return Triple(d, m, y)
}