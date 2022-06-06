package com.onopry.budgetapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.services.PeriodService
import com.onopry.budgetapp.model.services.TargetService
import kotlinx.coroutines.*

class MainViewModel(
    private val operationsService: OperationsService,
    private val categoriesService: CategoriesService,
    private val targetService: TargetService,
    private val periodService: PeriodService
): ViewModel() {

    fun loadServices() {
        Log.d("HUITAAAA", "VIEWMODEL load start")
//        val TAG = "MainViewModel_LOADSERVICES_TAG"
//        Log.d(TAG, "loadServices: start")
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "in 1st scope start")
            runBlocking {
                Log.d("HUITAAAA", "VIEWMODEL in start runBlocking for CATEGORIES")
//                Log.d(TAG, "in 1st block start")
                categoriesService.loadCategories()
//                Log.d(TAG, "in 1st block end")
                Log.d("HUITAAAA", "VIEWMODEL in end runBlocking for CATEGORIES")
            }
/*            runBlocking {
//                Log.d(TAG, "in 2nd block start")
                Log.d("HUITAAAA", "VIEWMODEL in start runBlocking for OPERATIONS")
                operationsService.loadOperations()
                Log.d("HUITAAAA", "VIEWMODEL in end runBlocking for OPERATIONS")
//                Log.d(TAG, "in 2nd block end")
            }*/
//            Log.d(TAG, "in 1st scope end")
        }
        Log.d("HUITAAAA", "VIEWMODEL load end")
    }
}