package com.onopry.budgetapp

import android.app.Application
import com.onopry.budgetapp.model.OperationsService

class App: Application() {
    val operationsService = OperationsService()
}