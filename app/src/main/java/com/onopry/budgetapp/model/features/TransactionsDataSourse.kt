package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.dto.OperationsDto

interface TransactionsDataSourse {
    fun getTransactions(): MutableList<OperationsDto>
    fun deleteTransaction(transaction: OperationsDto)
    fun editTransaction(transaction: OperationsDto)
    fun addTransaction(transaction: OperationsDto)
}