package com.onopry.budgetapp.viewmodel.screens.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.services.OperationsListener
import com.onopry.budgetapp.model.services.OperationsService

class OperationsViewModel(
    private val operationsService: OperationsService
): ViewModel() {

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    private val listener: OperationsListener = {
        _operations.value = it
    }

    init {
        loadOperations()
//        initFirebase()
//        operations.value?.get(3)?.let { operationsService.updateFirebaseOperations(it.toMap()) }
    }

    private fun loadOperations(){
        operationsService.addListener(listener)
    }

    fun deleteOperation(operation: OperationsDto){
        operationsService.deleteOperation(operation)
    }

    override fun onCleared() {
        super.onCleared()
//        operationsService.removeListener(listener)
    }
}