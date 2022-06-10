package com.onopry.budgetapp.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.dto.OperationsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddingMoneyViewModel @Inject constructor(
    private val operationsService: OperationsService
): ViewModel() {

    private val _operation = MutableLiveData<OperationsDto>()
    val operation: LiveData<OperationsDto> = _operation

    // todo: Добавить коллбек от результата добавления, чтобы прокидывать его прям до фрагмента, и при успешном завершении - закрывать фрагмент
    fun addOperation(operation: OperationsDto){
        viewModelScope.launch(Dispatchers.IO) {
            operationsService.addOperation(
                operation.copy(
                    id = UUID.randomUUID().toString()
                )
            )
        }
/*        viewModelScope.launch(Dispatchers.IO) {
            operationsService.addOperationFirebase(
                operation.copy(id = id))
        }*/
    }
}