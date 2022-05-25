package com.onopry.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onopry.budgetapp.model.OperationsListener
import com.onopry.budgetapp.model.OperationsService
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import java.time.LocalDate

class AnalyticsViewModel(
    private val operationsService: OperationsService
): ViewModel() {

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    private val _period = MutableLiveData<Pair<LocalDate, LocalDate>>()
    val period: LiveData<Pair<LocalDate, LocalDate>> = _period

    private val _operationsByPeriod = MutableLiveData<List<OperationsDto>>()
    val operationsByPeriod: LiveData<List<OperationsDto>> = _operationsByPeriod

    val currentDate = LocalDate.now()

    private val _date = MutableLiveData<LocalDate>()
    val date: LiveData<LocalDate> = _date

//    private val _amountByCategoryMap = MutableLiveData<Map<Cate>>

//    private val


    private val listener: OperationsListener = {
        _operations.value = it
    }


    init {
        loadOperations()
        initCurrentDate()
        initPeriod()
    }

    private fun initCurrentDate(){
        _date.value = LocalDate.now()
    }

    private fun initPeriod(){
        val startDate: LocalDate = LocalDate.of(currentDate.year, currentDate.monthValue, 1)
        val endDate = LocalDate.of(currentDate.year, currentDate.monthValue, currentDate.lengthOfMonth())
        _period.value = Pair(startDate, endDate)
    }

    private fun loadOperations(){
        operationsService.addListener(listener)
    }

    fun getOperationsByDay(dateOfDay: LocalDate){
        _operationsByPeriod.value = operationsService.getOperationsByDay(dateOfDay)
    }

    fun getOperationsByMonth(dateOfMonth: LocalDate) {
        _operationsByPeriod.value = operationsService.getOperationsByMonth(dateOfMonth)
    }

    fun getOperationsByYear(dateOfYear: LocalDate){
        _operationsByPeriod.value = operationsService.getOperationsByYear(dateOfYear)
    }

    fun getOperationByPeriod(startDate: LocalDate, finishDate: LocalDate){
        _operationsByPeriod.value = operationsService.getOperationByPeriod(startDate, finishDate)
    }

    fun getSumByPeriod(startDate: LocalDate, finishDate: LocalDate) = operationsService.getSumExpencesByPeriod(startDate, finishDate)

    // todo: можно пизже (сильно сильно сильно сильно пизже)
    fun getCategoryExpencesMap(): Map<CategoriesDto, Int> {
        val categoryExpencesMap = mutableMapOf<CategoriesDto, Int>()
        // получаем операции за текущий(выбранный) период
        val operationsByPeriod = operationsService.getOperationByPeriod(period.value!!.first, period.value!!.second)

        //получаем сет уникальных категорий из списка выше
        val currCategorySet = mutableSetOf<CategoriesDto>()
        operationsByPeriod.forEach {
            currCategorySet.add(it.category)
        }

        currCategorySet.forEach { category_ ->
            val sumAmountByCategory = operationsByPeriod
                .filter { operation -> // получаем список, состоящий только из операций одной категории
                    operation.category.name == category_.name && operation.isExpence }
                .sumOf { it.amount } // суммируем значения списка выше в переменную

            categoryExpencesMap[category_] = sumAmountByCategory
        }
        return categoryExpencesMap
    }





    override fun onCleared() {
        super.onCleared()
        operationsService.removeListener(listener)
    }


}