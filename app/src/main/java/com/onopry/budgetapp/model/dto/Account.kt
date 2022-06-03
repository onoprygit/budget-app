package com.onopry.budgetapp.model.dto

import android.graphics.Color
import com.onopry.budgetapp.R

data class Account(
    val id: String,
    val name: String = "default",
    val currency: String = "rub",
    val icon: Int = R.drawable.ic_acc_placeholder,
    val color: Int = Color.GREEN
)
