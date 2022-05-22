package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.TransactionService
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto
import java.util.*

class AddingMoneyViewModel(
    private val transactionService: TransactionService
): ViewModel() {

    private val _transaction = MutableLiveData<TransactionsDto>()
    val transaction: LiveData<TransactionsDto> = _transaction

    fun addTransaction(newOperation: TransactionsDto){
        val transaction = TransactionsDto(
            id = UUID.randomUUID().toString(),
            amount = newOperation.amount,
            category = newOperation.category,
            date = newOperation.date
        )
        transactionService.addTransaction(transaction)
    }
}