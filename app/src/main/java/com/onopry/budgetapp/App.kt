package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()