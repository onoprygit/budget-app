package com.onopry.budgetapp.utils

import android.content.res.Resources
import android.view.View
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.repo.FirebaseHelper
import java.time.LocalDate
import java.time.Month
import kotlin.math.roundToInt


/** extension func returns day, month, year
 * @param first day
 * @param second month
 * @param third year
 **/
fun LocalDate.getTextLocalDateDMY(): Triple<String, String, String>{
    val d = dayOfMonth.toString()
    val m = getTextMonth(false)
    val y = year.toString()
    return Triple(d, m, y)
}

/** extension func returns month, year
 * @param second month
 * @param third year
 **/
fun LocalDate.getTextLocalDateMY(): Pair<String, String>{
    val m = getTextMonth(true)
    val y = year.toString()
    return Pair(m, y)
}

/** returns
 * @param [case] required word of month form.
 * When [case] is true return will be simple form
 **/
fun LocalDate.getTextMonth(case: Boolean): String {
    val monthName: String
    if (case) {
        monthName = when (month.value) {
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
            else -> "Косяк"
        }
    } else {
        monthName = when (month.value) {
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
    }
    return monthName
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove(){
    this.visibility = View.GONE
}