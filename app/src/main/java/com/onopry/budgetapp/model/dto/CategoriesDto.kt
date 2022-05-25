package com.onopry.budgetapp.model.dto

import android.os.Parcel
import android.os.Parcelable
import android.view.ViewParent
import com.onopry.budgetapp.utils.MY_COLORS
import java.io.Serializable

data class CategoriesDto(
    val id: String,
    val name: String,
    val icon: Int,
    val color:Int = MY_COLORS.color_category_other,
    val isParent: Boolean = true,
    val isExpence: Boolean = true,
    val parentId: String? = null
): Serializable