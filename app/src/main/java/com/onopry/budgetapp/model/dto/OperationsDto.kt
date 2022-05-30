package com.onopry.budgetapp.model.dto

import android.os.Parcelable
import com.onopry.budgetapp.model.dto.CategoriesDto
import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class OperationsDto(
    val id: String,
    var amount: Int,
    var category: CategoriesDto,
    var date: LocalDate = LocalDate.of(2022,1,1),
    var isExpence: Boolean = true
): Serializable




