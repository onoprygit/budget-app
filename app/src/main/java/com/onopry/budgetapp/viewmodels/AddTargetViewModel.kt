package com.onopry.budgetapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.CategoriesService
import com.onopry.budgetapp.model.TargetService
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.utils.MY_COLORS
import com.onopry.budgetapp.utils.TargetNotFoundException
import java.util.*

class AddTargetViewModel(
    private val categoryService: CategoriesService,
    private val targetService: TargetService
): ViewModel() {

    private val _targets = MutableLiveData<List<TargetDTO>>()
    val targets: LiveData<List<TargetDTO>> = _targets

    init {
        targetService.addListener { _targets.value = it }
    }

    fun getTargetById(id: String?): TargetDTO? {
        if (id != null) {
            return targetService.getTargetById(id)
        } else
            return null
    }

    fun saveTarget(target: TargetDTO){
        if (targetService.isTargetExist(target))
            targetService.editTarget(target)
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


}