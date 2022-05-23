package com.onopry.budgetapp.model.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CategoriesDto(
    val name: String?,
    val icon: Int,
    val color:Int = -1
): Serializable