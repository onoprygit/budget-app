package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.CategoriesListener
import com.onopry.budgetapp.model.CategoriesService
import com.onopry.budgetapp.model.dto.CategoriesDto

class CategoryBottomSheetViewModel(
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