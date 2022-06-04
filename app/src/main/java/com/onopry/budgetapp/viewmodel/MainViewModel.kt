package com.onopry.budgetapp.viewmodel

import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService

class MainViewModel(
    private val operationsService: OperationsService,
    private val categoriesService: CategoriesService,
    private val targetService: TargetService,
    private val periodService: PeriodService
): ViewModel() {

    fun loadServices(){
        operationsService.loadOperations()
        categoriesService.loadCategories()
    }
}