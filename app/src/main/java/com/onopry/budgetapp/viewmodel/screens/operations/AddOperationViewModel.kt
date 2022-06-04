package com.onopry.budgetapp.viewmodel.screens.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.services.OperationsService
import java.util.*

class AddingMoneyViewModel(
    private val operationsService: OperationsService
): ViewModel() {

    private val _operation = MutableLiveData<OperationsDto>()
    val operation: LiveData<OperationsDto> = _operation

    fun addOperation(operation: OperationsDto){
        operationsService.addOperation(
            operation.copy(
                id = UUID.randomUUID().toString()
            )
        )
    }
}