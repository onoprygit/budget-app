package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.TransactionService

class AnalyticsViewModel(
    private val transactionService: TransactionService
): ViewModel() {
}