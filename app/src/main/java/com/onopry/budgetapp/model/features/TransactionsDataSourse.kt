package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.dto.TransactionsDto

interface TransactionsDataSourse {
    fun getTransactions(): MutableList<TransactionsDto>
}