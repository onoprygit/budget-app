package com.onopry.budgetapp.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.dto.OperationsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddingMoneyViewModel @Inject constructor(
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