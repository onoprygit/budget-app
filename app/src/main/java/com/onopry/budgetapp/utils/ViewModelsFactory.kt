package com.onopry.budgetapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.App
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
    private val app: App
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){

            AddingMoneyViewModel::class.java -> AddingMoneyViewModel(app.operationsService)
            AnalyticsViewModel::class.java -> AnalyticsViewModel(app.operationsService, app.periodService)
            BudgetAndDebtsViewModel::class.java -> BudgetAndDebtsViewModel(app.targetService)
            EditOperationViewModel::class.java -> EditOperationViewModel(app.operationsService)
            MoreViewModel::class.java -> MoreViewModel(app.operationsService)
            OperationsViewModel::class.java -> OperationsViewModel(app.operationsService)
            CategoryBottomSheetViewModel::class.java -> CategoryBottomSheetViewModel(app.categoriesService)
            AddTargetViewModel::class.java -> AddTargetViewModel(app.categoriesService, app.targetService)

            AuthViewModel::class.java -> AuthViewModel(app.categoriesService)
//            MainViewModel::class.java -> MainViewModel(app.mainRepository)

            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }
}
