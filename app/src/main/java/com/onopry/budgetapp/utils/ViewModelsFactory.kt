package com.onopry.budgetapp.utils

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.App
import com.onopry.budgetapp.viewmodels.*

class ViewModelsFactory(
    private val app: App
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){

            AddingMoneyViewModel::class.java -> AddingMoneyViewModel(app.transactionsService)
            AnalyticsViewModel::class.java -> AnalyticsViewModel(app.transactionsService)
            BudgetAndDebtsViewModel::class.java -> BudgetAndDebtsViewModel(app.transactionsService)
            MoreViewModel::class.java -> MoreViewModel(app.transactionsService)
            TransactionsViewModel::class.java -> TransactionsViewModel(app.transactionsService)

            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }
}

// TODO: разобраться с App и в целом с фабрикой
fun Fragment.startFactory() = ViewModelsFactory(requireContext().applicationContext as App)