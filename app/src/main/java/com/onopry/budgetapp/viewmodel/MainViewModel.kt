package com.onopry.budgetapp.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.TargetService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoriesService: CategoriesService,
    private val operationsService: OperationsService,
    private val targetService: TargetService
): ViewModel() {

    fun generateDefaultUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            val cats = categoriesService.generateDefaultUserCategories()
            operationsService.generateDefaultUserOperations(cats)
        }
    }
}
