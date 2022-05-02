package com.onopry.budgetapp.dataStarage

class TransactionDataStorage {
    private val transactionList = mutableListOf<Int>()

    fun addTransaction(transaction: Int){
        transactionList.add(transaction)
    }

    fun deleteTransaction(transactionId: Int){
        transactionList.removeAt(transactionId)
    }
}