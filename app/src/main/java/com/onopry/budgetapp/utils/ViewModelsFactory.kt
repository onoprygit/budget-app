package com.onopry.budgetapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.App
import com.onopry.budgetapp.viewmodel.*
import com.onopry.budgetapp.viewmodel.screens.analytics.AnalyticsViewModel
import com.onopry.budgetapp.viewmodel.screens.more.MoreViewModel
import com.onopry.budgetapp.viewmodel.screens.operations.AddingMoneyViewModel
import com.onopry.budgetapp.viewmodel.screens.operations.EditOperationViewModel
import com.onopry.budgetapp.viewmodel.screens.operations.OperationsViewModel
import com.onopry.budgetapp.viewmodel.screens.targets.AddTargetViewModel
import com.onopry.budgetapp.viewmodel.screens.targets.BudgetAndDebtsViewModel

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

            MainViewModel::class.java -> MainViewModel(app.operationsService, app.categoriesService, app.targetService, app.periodService)

            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }
}
