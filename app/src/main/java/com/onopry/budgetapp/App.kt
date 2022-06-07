package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.repo.RealTimeDBRepository
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService

class App: Application() {
    val categoriesService = CategoriesService()
    val targetService = TargetService(categoriesService)
    val operationsService = OperationsService(targetService, categoriesService)
    val periodService = PeriodService()

    //val mainRepository = RealTimeDBRepository()
}