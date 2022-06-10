package com.onopry.budgetapp.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.utils.OperationIdNotFoundException
import com.onopry.budgetapp.utils.OperationNotFoundException
import com.onopry.budgetapp.utils.OperationParamsNotFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditOperationViewModel @Inject constructor(
    private val operationsService: OperationsService
): ViewModel() {
    private val _operation = MutableLiveData<OperationsDto>()
    val operation: LiveData<OperationsDto> = _operation

    fun setOperation(operationId: String?){
        if (operationId == null) throw OperationNotFoundException()
        _operation.value = operationsService.getOperationById(operationId)
    }

    fun editOperation(newOperation: OperationsDto){
        val operationToAdd = OperationsDto(
            id = operation.value?.id ?: throw OperationIdNotFoundException(),
            amount = newOperation.amount,
            category = newOperation.category,
            date = operation.value?.date ?: throw OperationParamsNotFoundException(),
        )
        operationsService.editOperation(operationToAdd)
    }
}