package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.CategoriesService
import com.onopry.budgetapp.model.OperationsService
import com.onopry.budgetapp.model.PeriodService
import com.onopry.budgetapp.model.TargetService

class App: Application() {
    val periodService = PeriodService()
    val targetService = TargetService()
    val categoriesService = CategoriesService()
    val operationsService = OperationsService(targetService)
}