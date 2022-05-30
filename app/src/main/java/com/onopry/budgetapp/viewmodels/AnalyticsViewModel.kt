package com.onopry.budgetapp.viewmodels
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.onopry.budgetapp.model.OperationsListener
import com.onopry.budgetapp.model.OperationsService
import com.onopry.budgetapp.model.PeriodListener
import com.onopry.budgetapp.model.PeriodService
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.utils.AmountByCategory
import com.onopry.budgetapp.utils.PeriodDate
import com.onopry.budgetapp.utils.PeriodRange
import java.time.LocalDate

class AnalyticsViewModel(
    private val operationsService: OperationsService,
    private val periodService: PeriodService
): ViewModel() {
//    var currentDate: LocalDate = LocalDate.now()
    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    private val _period = MutableLiveData<PeriodDate>()
    val period: LiveData<PeriodDate> = _period

    private val _operationsByCategory = MutableLiveData<Map<CategoriesDto, List<OperationsDto>>>()
    var operationsByCategory: LiveData<Map<CategoriesDto, List<OperationsDto>>> = _operationsByCategory

    private val periodListener: PeriodListener = { _period.value = it }
    private val operationListener: OperationsListener = { _operations.value = it }

    init {
        loadOperations()
        initPeriod()
        setOperationsByCategory()
        Log.d("L_IFECYCLE_TAG_", "init")
    }

    private fun loadOperations(){
        operationsService.addListener(operationListener)
    }

    private fun initPeriod(){
        periodService.addListener(periodListener)
    }

    // Period settings
    fun setPeriod(startDate: LocalDate, finishDate: LocalDate){ periodService.setPeriod(startDate, finishDate, PeriodRange.OTHER) }

    fun setPeriod(date: LocalDate, typePeriod: PeriodRange){
        periodService.setPeriod(date, typePeriod)
        setOperationsByCategory()
        Log.d("ChooseButtonTAG", "viewModel setPeriod(): date = $date, period = $typePeriod")
    }

    // Operations by category
    private fun setOperationsByCategory(){
        _operationsByCategory.value = loadOperationsByCategory(_period.value!!.startDate, _period.value!!.finishDate)
        Log.d("TAG", "setOperationsByCategory: ${_period.value!!.startDate}, ${_period.value!!.finishDate}, ${_period.value!!.periodRange}")
    }

    private fun loadOperationsByCategory(startDate: LocalDate, finishDate: LocalDate): MutableMap<CategoriesDto, List<OperationsDto>> {
        val map = mutableMapOf<CategoriesDto, List<OperationsDto>>()

        val categorySet = mutableSetOf<CategoriesDto>()
        val operList: List<OperationsDto> = operationsService.getOperationByPeriod(startDate, finishDate).filter { it.isExpence }
        operList.forEach { categorySet.add(it.category) }
        categorySet.forEach { category ->
            map[category] = operList.filter { it.category.id == category.id && it.isExpence }
        }
        return map
    }

    fun getSumAmountByCategory(): List<AmountByCategory>{
        val list: MutableList<AmountByCategory> = mutableListOf()
        operationsService.getOperationsByCategorySumAmount(_operationsByCategory.value!!).forEach {
            list.add(AmountByCategory(it.key, it.value))
        }
        list.filter { it.amount > 0 }
        list.sortByDescending { it.amount }
        return list
    }

    fun getAmountByPeriod() = operationsService.getSumExpencesByPeriod(_period.value!!)


    // Pie chart methods
    fun getPieEntriesList(): List<PieEntry>{
        val pieEntries = mutableListOf<PieEntry>()
        getSumAmountByCategory().forEach {
            if (it.amount > 0)
                pieEntries.add(PieEntry(it.amount.toFloat(), it.category.name))
        }
        return pieEntries
    }

    fun getCategoriesColors() = _operationsByCategory.value?.keys?.map { it.color }?.toList() ?: throw Exception("No colors")

    override fun onCleared() {
//        operationsService.removeListener(operationListener)
//        periodService.removeListener(periodListener)
        super.onCleared()
        Log.d("L_IFECYCLE_TAG_", "onCleared: ")
    }
}