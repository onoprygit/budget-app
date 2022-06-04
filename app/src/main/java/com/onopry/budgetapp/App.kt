package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.*
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService

class App: Application() {
    val targetService = TargetService()
    val categoriesService = CategoriesService()
    val operationsService = OperationsService(targetService, categoriesService)
    val periodService = PeriodService()

/*    init {
        initFirebase()
    }*/
}