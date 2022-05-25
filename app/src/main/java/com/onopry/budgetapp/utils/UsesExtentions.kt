package com.onopry.budgetapp.utils

import java.time.LocalDate

/** extension func returns day, month, year
 * @param first day
 * @param second month
 * @param third year
 **/
fun LocalDate.getTextLocalDate(): Triple<String, String, String>{
    val d = dayOfMonth.toString()
    val m = when(month.value){
        1 -> "Январь"
        2 -> "Февраль"
        3 -> "Март"
        4 -> "Апрель"
        5 -> "Май"
        6 -> "Июнь"
        7 -> "Июль"
        8 -> "Август"
        9 -> "Сентябрь"
        10 -> "Октябрь"
        11 -> "Ноябрь"
        12 -> "Декабрь"
        else -> throw DateNotExistException()
    }
    val y = year.toString()
    return Triple(d, m, y)
}