package com.onopry.budgetapp.viewmodel.more

import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.OperationsService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val operationsService: OperationsService
): ViewModel() {
}