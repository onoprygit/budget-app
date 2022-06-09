package com.onopry.budgetapp.utils

import android.graphics.Color

object LogTags {
    const val FIREBASE_DATA_LISTENER_TAG = "FIREBASE_DATA_LISTENER_TAG"
    const val ADD_TARGET_VIEW_MODEL = "ADDING_MONEY_FRAGMENT_TAG"
    const val ADDING_MONEY_FRAGMENT_TAG = "ADDING_MONEY_FRAGMENT_TAG"
    const val ANALYTICS_FRAGMENT_TAG = "ANALYTICS_FRAGMENT_TAG"
    const val BUDGET_ADN_DEBTS_FRAGMENT_TAG = "BUDGET_ADN_DEBTS_FRAGMENT_TAG"
    const val CATEGORY_BOTTOMSHEET_FRAGMENT_TAG = "CATEGORY_BOTTOMSHEET_FRAGMENT_TAG"
    const val MORE_FRAGMENT_TAG = "MORE_FRAGMENT_TAG"
    const val TRANSACTION_FRAGMENT_TAG = "TRANSACTION_FRAGMENT_TAG"

    const val GENERATE_DATA_TAG = "GENERATE_DATA_TAG"
    const val FETCH_DATA_TAG = "FETCH_DATA_TAG"
    const val DI_INSTANCES_TAG = "DI_INSTANCES_TAG"
}

object MY_COLORS{
    val color_category_1 = Color.parseColor("#EDA297")
    val color_category_2 = Color.parseColor("#A7D7A2")
//    val color_category_3 = Color.parseColor("#A7D2A1")
    val color_category_4 = Color.parseColor("#CE97DB")
    val color_category_5 = Color.parseColor("#9CAFD0")
    val color_category_6 = Color.parseColor("#83CAC2")
    val color_category_7 = Color.parseColor("#8FC5F8")
    val color_category_8 = Color.parseColor("#FAE285")
    val color_category_9 = Color.parseColor("#F9FC95")
    val color_category_10 = Color.parseColor("#F8A693")
//    val color_category_11 = Color.parseColor("#B998DA")
    val color_category_12 = Color.parseColor("#B89AD5")
    val color_category_13 = Color.parseColor("#FFCB7C")
    val color_category_14 = Color.parseColor("#F28CB4")
    val color_category_15 = Color.parseColor("#E7EB9C")
    val color_category_other = Color.parseColor("#AEC0C3")
}


enum class PeriodRange{
    WEEK, MONTH, YEAR, OTHER
}

object FIREBASE {
    const val DATABASE_URL = "https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app"
}
