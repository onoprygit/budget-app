package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.TransactionService
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto
import java.util.*

class AddingMoneyViewModel(
    private val transactionService: TransactionService
): ViewModel() {
    fun addTransaction(money: Int ,category: CategoriesDto){
        val transaction = TransactionsDto(
            id = UUID.randomUUID(),
            amount = money,
            category = category
        )
        transactionService.addTransaction(transaction)
    }
}