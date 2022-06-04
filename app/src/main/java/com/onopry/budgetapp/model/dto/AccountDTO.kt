package com.onopry.budgetapp.model.dto

import android.graphics.Color
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.*
import java.io.Serializable

data class AccountDTO(
    val id: String = "default",
    val name: String = "default",
    val currency: String = "rub",
    val icon: Int = R.drawable.ic_acc_placeholder,
    val color: Int = Color.GREEN
): Serializable{
    fun toMap() = mutableMapOf<String, Any>(
        CHILD_ACCOUNT_ID to id,
        CHILD_ACCOUNT_NAME to name,
        CHILD_ACCOUNT_CURRENCY to currency,
        CHILD_ACCOUNT_ICON to icon,
        CHILD_ACCOUNT_COLOR to color,
    )
}
