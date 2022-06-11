package com.onopry.budgetapp.viewmodel.budgetanddebts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.services.TargetListener
import com.onopry.budgetapp.model.services.TargetService
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.services.CategoriesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BudgetAndDebtsViewModel @Inject constructor(
    private val targetService: TargetService,
    private val categoryService: CategoriesService
): ViewModel() {

    private val _targets = MutableLiveData<List<TargetDTO>>()
    val targets: LiveData<List<TargetDTO>> = _targets

    val tars = targetService.targets

    private val listener: TargetListener = {
        _targets.value = it
    }

    init {
        loadTargets()
    }

    private fun loadTargets(){ targetService.addListener(listener) }

    fun getTargetById(id: String?): TargetDTO? {
        if (id != null) {
            return targetService.getTargetById(id)
        } else
            return null
    }

    fun saveTarget(target: TargetDTO){
        if (targetService.isTargetExist(target))
            viewModelScope.launch {
                targetService.editTarget(target)
            }
        else {
            targetService.addTarget(target)
            categoryService.addCategory(
                CategoriesDto(
                    id = UUID.randomUUID().toString(),
                    name = target.title,
                    icon = R.drawable.ic_category_placeholder,
                    targetId = target.id
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }


}