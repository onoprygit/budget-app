package com.onopry.budgetapp.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigator
import com.onopry.budgetapp.App


// TODO: разобраться с App и в целом с фабрикой
/** Экстеншн для фабрики, чтобы удобно создавать ВьюМодели */
//fun Fragment.startFactory() = ViewModelsFactory()

/** Для доступа к возможностям навигации из Activity */
fun Fragment.navigator() = requireActivity() as MainNavigator

//fun Activity.startFactory() = ViewModelsFactory(applicationContext as App)