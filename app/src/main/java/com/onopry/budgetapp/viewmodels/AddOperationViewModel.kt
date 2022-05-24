package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.OperationsService
import com.onopry.budgetapp.model.dto.OperationsDto
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