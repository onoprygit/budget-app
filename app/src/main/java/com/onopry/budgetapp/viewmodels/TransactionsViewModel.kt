package com.onopry.budgetapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.TransactionService
import com.onopry.budgetapp.model.TransactionsListener
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto

class TransactionsViewModel(
    private val transactionService: TransactionService
): ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionsDto>>()
    val transactions: LiveData<List<TransactionsDto>> = _transactions

    private val listener: TransactionsListener = {
        _transactions.value = it
    }

    init {
        loadTransactions()
    }

    fun loadTransactions(){
        transactionService.addListener(listener)
    }

    fun deleteTransaction(transaction: TransactionsDto){
        transactionService.deleteTransaction(transaction)
    }

    fun addTransaction(transaction: TransactionsDto){
        transactionService.addTransaction(transaction)
    }

    fun editTransaction(transaction: TransactionsDto, id: Int, newAmount:Int, newCategory: CategoriesDto){
        transactionService.editTransaction(transaction, id, newAmount, newCategory)
    }

    override fun onCleared() {
        super.onCleared()
        transactionService.removeListener(listener)
    }
}