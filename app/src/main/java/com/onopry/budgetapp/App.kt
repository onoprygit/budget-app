package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.CategoriesService
import com.onopry.budgetapp.model.OperationsService
import com.onopry.budgetapp.model.PeriodService
import com.onopry.budgetapp.model.TargetService

class App: Application() {
    val targetService = TargetService()
    val operationsService = OperationsService(targetService)
    val categoriesService = CategoriesService()
    val periodService = PeriodService()
}