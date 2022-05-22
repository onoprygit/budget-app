package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.TransactionService
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.utils.OperationIdNotFoundException
import com.onopry.budgetapp.utils.OperationNotFoundException
import com.onopry.budgetapp.utils.OperationParamsNotFoundException

class EditOperationViewModel(
    private val transactionService: TransactionService
): ViewModel() {
    private val _operation = MutableLiveData<TransactionsDto>()
    val operation: LiveData<TransactionsDto> = _operation

    fun setOperation(operationId: String?){
        if (operationId == null) throw OperationNotFoundException()
        val operation = transactionService.getOperationById(operationId)
        _operation.value = operation
    }

    fun editOperation(newOperation: TransactionsDto){
        val operationToAdd = TransactionsDto(
            id = operation.value?.id ?: throw OperationIdNotFoundException(),
            amount = newOperation.amount,
            category = newOperation.category,
            date = operation.value?.date ?: throw OperationParamsNotFoundException(),
        )
        transactionService.editTransaction(operationToAdd)

    }
}