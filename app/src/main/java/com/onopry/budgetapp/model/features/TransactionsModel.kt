package com.onopry.budgetapp.model.features

class TransactionsModel(
    private val transactionsDataSourse: TransactionsDataSourse
) {
    fun getTransactions() = transactionsDataSourse.getTransactions()
}