package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.TransactionService

class App: Application() {
    val transactionsService = TransactionService()
}