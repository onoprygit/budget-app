package com.onopry.budgetapp.viewmodel.operations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.OperationsListener
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.utils.initFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OperationsViewModel @Inject constructor(
    private val operationsService: OperationsService
): ViewModel() {
    val TAG = "FRAGMENTDESTROY_TAG"

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    val _operations_ = operationsService.operations

    private val listener: OperationsListener = {
        _operations.value = it
    }

    init {
        Log.d(TAG, "ViewModel is init")
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
        operationsService.removeListener(listener)
        Log.d(TAG, "ViewModel is cleared")
    }

    fun getCategoryById(id: String) = operationsService.getCategoryById(id)

    fun getService() = operationsService
}