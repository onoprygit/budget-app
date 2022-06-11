package com.onopry.budgetapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.model.services.AccountsService
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.TargetService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoriesService: CategoriesService,
    private val operationsService: OperationsService,
    private val targetService: TargetService,
    private val accountsService: AccountsService
): ViewModel() {

    fun generateDefaultUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            val accId = accountsService.generateDefaultUserAccountAsync()
            val cats = categoriesService.generateDefaultUserCategoriesAsync()
            operationsService.generateDefaultUserOperations(cats.await(), accId.await())
        }
    }
}
