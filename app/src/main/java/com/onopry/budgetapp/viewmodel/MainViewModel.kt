package com.onopry.budgetapp.viewmodel

/*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.repo.RealTimeDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    repository: RealTimeDBRepository
): ViewModel() {

    */
/*private val _categories = MutableLiveData<List<CategoriesDto>>()
    val categories: LiveData<List<CategoriesDto>> = _categories*//*


    val categories: LiveData<List<CategoriesDto>> = repository.categoryRepository.categoriesLiveData

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    private val _targets = MutableLiveData<List<TargetDTO>>()
    val targets: LiveData<List<TargetDTO>> = _targets

//    val _accounts = MutableLiveData<List<OperationsDto>>()
//    val accounts: LiveData<List<OperationsDto>> = _accounts
//
//    val _selectedPeriod = MutableLiveData<List<OperationsDto>>()
//    val selectedPeriod: LiveData<List<OperationsDto>> = _selectedPeriod
}*/
