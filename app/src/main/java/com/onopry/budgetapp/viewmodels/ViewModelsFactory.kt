package com.onopry.budgetapp.viewmodels

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.App

class ViewModelsFactory(
    private val app: App
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){

            TransactionsViewModel::class.java -> TransactionsViewModel(app.transactionsService)

            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }
}

// TODO: разобраться с App и в целом с фабрикой
fun Fragment.startFactory() = ViewModelsFactory(requireContext().applicationContext as App)