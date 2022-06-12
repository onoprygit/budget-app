package com.onopry.budgetapp.model.dto

import android.graphics.Color
import com.google.firebase.database.DataSnapshot
import com.onopry.budgetapp.R
import com.onopry.budgetapp.utils.ACCOUNT

data class AccountDto(
    val id: String,
    val name: String = "default",
    val currency: String = "rub",
    val icon: Int = R.drawable.ic_acc_placeholder,
    val color: Int = Color.GREEN,
    val type: String = "CARD" // "BANK", ""
){
    fun toMap() = mapOf<String, Any>(
        ACCOUNT.NAME to name ,
        ACCOUNT.CURRENCY to currency,
        ACCOUNT.ICON to icon,
        ACCOUNT.COLOR to color,
        ACCOUNT.TYPE to type
    )

    companion object {
        fun parseSnapshot(snapshot: DataSnapshot) = AccountDto(
            id = snapshot.key as String,
            name = snapshot.child(ACCOUNT.NAME).value as String,
            currency = snapshot.child(ACCOUNT.CURRENCY).value as String,
            icon = (snapshot.child(ACCOUNT.ICON).value as Long).toInt(),
            color = (snapshot.child(ACCOUNT.COLOR).value as Long).toInt(),
            type = snapshot.child(ACCOUNT.TYPE).value as String
        )
    }
}
