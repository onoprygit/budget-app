package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.Transactions

interface TransactionsDataSourse {
    fun getTransactions(): MutableList<Transactions>
}