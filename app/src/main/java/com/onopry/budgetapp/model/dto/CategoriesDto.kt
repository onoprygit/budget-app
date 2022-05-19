package com.onopry.budgetapp.model.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CategoriesDto(
    val category: String?,
    val icon: Int
): Serializable