package com.onopry.budgetapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.App
import com.onopry.budgetapp.viewmodels.*

class ViewModelsFactory(
    private val app: App
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){

            AddingMoneyViewModel::class.java -> AddingMoneyViewModel(app.operationsService)
            AnalyticsViewModel::class.java -> AnalyticsViewModel(app.operationsService)
            BudgetAndDebtsViewModel::class.java -> BudgetAndDebtsViewModel(app.operationsService)
            EditOperationViewModel::class.java -> EditOperationViewModel(app.operationsService)
            MoreViewModel::class.java -> MoreViewModel(app.operationsService)
            OperationsViewModel::class.java -> OperationsViewModel(app.operationsService)
            CategoryBottomSheetViewModel::class.java -> CategoryBottomSheetViewModel(app.categoriesService)

            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }
}
