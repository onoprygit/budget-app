package com.onopry.budgetapp.viewmodel.budgetanddebts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.TargetService
import com.onopry.budgetapp.model.dto.TargetDto
import com.onopry.budgetapp.utils.LogTags
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTargetViewModel @Inject constructor(
    private val categoryService: CategoriesService,
    private val targetService: TargetService
): ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d(LogTags.ADD_TARGET_VIEW_MODEL, "onCleared: ")
    }

    private val _targets = MutableLiveData<List<TargetDto>>()
    val targets: LiveData<List<TargetDto>> = _targets

    init {
        targetService.addListener { _targets.value = it }
    }

    fun getTargetById(id: String?): TargetDto? {
        if (id != null) {
            return targetService.getTargetById(id)
        } else
            return null
    }

//    fun saveTarget(target: TargetDTO){
//        if (targetService.isTargetExist(target))
//            targetService.editTarget(target)
//        else {
//            targetService.addTarget(target)
//            categoryService.addCategory(
//                CategoriesDto(
//                    id = UUID.randomUUID().toString(),
//                    name = target.title,
//                    icon = R.drawable.ic_category_placeholder,
//                    targetId = target.id
//                )
//            )
//        }
//    }


}