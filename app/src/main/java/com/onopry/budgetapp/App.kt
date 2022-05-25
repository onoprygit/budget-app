package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.CategoriesService
import com.onopry.budgetapp.model.OperationsService

class App: Application() {
    val categoriesService = CategoriesService()
    val operationsService = OperationsService(categoriesService)
}