package com.onopry.budgetapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.CategoriesListener
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.dto.CategoriesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryBottomSheetViewModel @Inject constructor(
    private val categoriesService: CategoriesService
): ViewModel() {
    private val _categoriesList = MutableLiveData<List<CategoriesDto>>()
    val categoriesList: LiveData<List<CategoriesDto>> = _categoriesList

    private val listener: CategoriesListener = {
        _categoriesList.value = it
    }

    init {
        loadCategories()
    }

    private fun loadCategories(){
        categoriesService.addListener(listener)
    }
}