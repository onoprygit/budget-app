package com.onopry.budgetapp.model.dto

import android.os.Parcelable
import com.onopry.budgetapp.model.dto.CategoriesDto
import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class TransactionsDto(
    val id: UUID,
    var amount: Int,
    var category: CategoriesDto,
//    var date: GregorianCalendar = GregorianCalendar(2022,1,1),
    var date: LocalDate = LocalDate.of(2022,1,1)
): Serializable

fun Date.getYearMonthDay() = "$year-$month-$day"
//fun GregorianCalendar.getYearMonthDay(){
//    ti
//}


