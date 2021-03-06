package com.onopry.budgetapp.viewmodel.screens.targets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.services.TargetListener
import com.onopry.budgetapp.model.services.TargetService

class BudgetAndDebtsViewModel(
    private val targetService: TargetService
): ViewModel() {

    private val _targets = MutableLiveData<List<TargetDTO>>()
    val targets: LiveData<List<TargetDTO>> = _targets

    private val listener: TargetListener = {
        _targets.value = it
    }

    init {
        loadTargets()
    }

    private fun loadTargets(){ targetService.addListener(listener) }



    override fun onCleared() {
        targetService.removeListener(listener)
        super.onCleared()
    }


}