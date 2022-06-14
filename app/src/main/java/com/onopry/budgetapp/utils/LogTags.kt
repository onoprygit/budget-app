package com.onopry.budgetapp.utils

import android.graphics.Color

object LogTags {
    const val FIREBASE_DATA_ADDING_TAG = "FIREBASE_DATA_ADDING_TAG"
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

object CATEGORIES_COLORS{
    val color_category_car= Color.parseColor("#ff9d9a")
    val color_category_products= Color.parseColor("#A0CBE8")
    val color_category_transport= Color.parseColor("#F28E2B")
    val color_category_cafe= Color.parseColor("#FFBE7D")
    val color_category_fashion= Color.parseColor("#ff9d9a")
    val color_category_house= Color.parseColor("#b6992d")
    val color_category_family= Color.parseColor("#f1ce63")
    val color_category_pets= Color.parseColor("#499894")
    val color_category_hobby= Color.parseColor("#86bcb6")
    val color_category_health = Color.parseColor("#e15759")
    val color_category_other = Color.parseColor("#AEC0C3")
    val color_category_bills = Color.parseColor("#bab0ac")
    val color_category_income = Color.parseColor("#59A14F")

    val color_category_beauty = Color.parseColor("#d37295")
    val color_category_14 = Color.parseColor("#d4a6c8")
    val color_category_15 = Color.parseColor("#d4a6c8")
    val color_category_16 = Color.parseColor("#d4a6c8")



//    val color_category_income_1 = Color.parseColor("#7CF795")
//    val color_category_income_main = Color.parseColor("#DDECD9")
}


enum class PeriodRange{
    WEEK, MONTH, YEAR, OTHER
}

object FIREBASE {
    const val DATABASE_URL = "https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app"
}
