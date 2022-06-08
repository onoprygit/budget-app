package com.onopry.budgetapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.App
import com.onopry.budgetapp.model.repo.RealTimeDBRepository
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService
import com.onopry.budgetapp.viewmodel.*
import com.onopry.budgetapp.viewmodel.analytics.AnalyticsViewModel
import com.onopry.budgetapp.viewmodel.auth.AuthViewModel
import com.onopry.budgetapp.viewmodel.budgetanddebts.AddTargetViewModel
import com.onopry.budgetapp.viewmodel.budgetanddebts.BudgetAndDebtsViewModel
import com.onopry.budgetapp.viewmodel.more.MoreViewModel
import com.onopry.budgetapp.viewmodel.operations.AddingMoneyViewModel
import com.onopry.budgetapp.viewmodel.operations.EditOperationViewModel
import com.onopry.budgetapp.viewmodel.operations.OperationsViewModel

class ViewModelsFactory(

): ViewModelProvider.Factory {

    val categoriesService = CategoriesService()
    val targetService = TargetService(categoriesService)
    val operationsService = OperationsService(targetService, categoriesService)
    val periodService = PeriodService()

    val repository = RealTimeDBRepository(categoriesService)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){

            AddingMoneyViewModel::class.java -> AddingMoneyViewModel(operationsService)
            AnalyticsViewModel::class.java -> AnalyticsViewModel(operationsService, periodService)
            BudgetAndDebtsViewModel::class.java -> BudgetAndDebtsViewModel(targetService)
            EditOperationViewModel::class.java -> EditOperationViewModel(operationsService)
            MoreViewModel::class.java -> MoreViewModel(operationsService)
            OperationsViewModel::class.java -> OperationsViewModel(operationsService)
            CategoryBottomSheetViewModel::class.java -> CategoryBottomSheetViewModel(categoriesService)
            AddTargetViewModel::class.java -> AddTargetViewModel(categoriesService, targetService)

            AuthViewModel::class.java -> AuthViewModel(categoriesService, repository)
            MainViewModel::class.java -> MainViewModel(repository)

            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }
}
