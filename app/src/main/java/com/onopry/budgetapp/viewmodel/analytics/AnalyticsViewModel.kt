package com.onopry.budgetapp.viewmodel.analytics
import android.util.Log
import androidx.lifecycle.*
import com.github.mikephil.charting.data.PieEntry
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.services.*
import com.onopry.budgetapp.utils.AmountByCategory
import com.onopry.budgetapp.utils.LogTags
import com.onopry.budgetapp.utils.PeriodDate
import com.onopry.budgetapp.utils.PeriodRange
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val operationsService: OperationsService,
    private val periodService: PeriodService,
    private val categoriesService: CategoriesService,
    private val accountsService: AccountsService
): ViewModel() {

    //todo походу нужна лайвдата для состояния кнопок и всякого такого

    private val _period = MutableLiveData<PeriodDate>()
    val period: LiveData<PeriodDate> = _period


    @Deprecated("HUITA")
    private val _operationsByCategory = MutableLiveData<Map<CategoriesDto, List<OperationsDto>>>()
    @Deprecated("HUITA")
    var operationsByCategory: LiveData<Map<CategoriesDto, List<OperationsDto>>> = _operationsByCategory

    val accounts = accountsService.accounts

//    private val _amountByCategory = MutableLiveData<>

    val operations = operationsService.operations

    /*todo("можно сделать одну мапу, в которую будут добавляться каатегории в кач-ве
       ключа при проходе по списку операций. а в кач-ве значения будет список,
       в который будем кидать операции через .add()")*/
    val sumByCategory = operationsService.operations.map { operationsList ->
        val categorySet = mutableSetOf<CategoriesDto>()
        operationsList.forEach {
            categorySet.add(it.category)
        }

        val mapCategories = mutableMapOf<CategoriesDto, List<OperationsDto>>()
        categorySet.forEach { category ->
            mapCategories[category] = operationsList.filter { operation ->
                operation.category == category
            }
        }

        mutableListOf<AmountByCategory>().apply {
            mapCategories.forEach { entires ->
                this.add(AmountByCategory(entires.key, entires.value.sumOf { it.amount }))
            }
        }.toList()


    }

    private val _typeState = MutableLiveData(true)
    val typeState: LiveData<Boolean> = _typeState

    private val periodListener: PeriodListener = { _period.value = it }

//    private val



    init {
        initPeriod()
        setOperationsByCategory()
        Log.d("L_IFECYCLE_TAG_FF", "init")
    }

    fun changeTypeState(isExpence: Boolean){
        _typeState.value = isExpence
    }

    private fun initPeriod(){
        periodService.addListener(periodListener)
    }

    // Period settings
    fun setPeriod(startDate: LocalDate, finishDate: LocalDate){
        periodService.setPeriod(startDate, finishDate, PeriodRange.OTHER)
    }

    fun setPeriod(date: LocalDate, typePeriod: PeriodRange){
        periodService.setPeriod(date, typePeriod)
        setOperationsByCategory()
        Log.d("ChooseButtonTAG", "viewModel setPeriod(): date = $date, period = $typePeriod")
    }

    // Operations by category
    fun setOperationsByCategory(){
        _operationsByCategory.value = loadOperationsByCategory(_period.value!!.startDate, _period.value!!.finishDate)
        Log.d(LogTags.ANALYTICS_FRAGMENT_TAG, "setOperationsByCategory: ${(_operationsByCategory.value as MutableMap<CategoriesDto, List<OperationsDto>>).size}")
    }

    private fun loadOperationsByCategory(startDate: LocalDate, finishDate: LocalDate): MutableMap<CategoriesDto, List<OperationsDto>> {
        val map = mutableMapOf<CategoriesDto, List<OperationsDto>>()

        val categorySet = mutableSetOf<CategoriesDto>()
        val operList: List<OperationsDto>? = operationsService.getOperationByPeriod(startDate, finishDate)
//            ?.filter { it.isExpence }

        operList?.forEach { categorySet.add(it.category) }
        categorySet.forEach { category ->
            map[category] = operList?.filter { it.category.id == category.id } ?: listOf()
        }
        Log.d(LogTags.ANALYTICS_FRAGMENT_TAG, "loadOperationsByCategory: map = ${map.size}, list = ${operList?.size}")
        return map
    }

    fun getSumAmountByCategory(): List<AmountByCategory>{
        val list: MutableList<AmountByCategory> = mutableListOf()
        operationsService.getOperationsByCategorySumAmount(_operationsByCategory.value!!)
            .forEach { list.add(AmountByCategory(it.key, it.value)) }
        list.filter { it.amount > 0 }
        list.sortByDescending { it.amount }
        return list
    }

    fun getAmountByPeriod(isExpence: Boolean) = operationsService.getSumExpencesByPeriod(_period.value!!, isExpence)


    // Pie chart methods
    fun getPieEntriesList(isExpence: Boolean): List<PieEntry>{
        val pieEntries = mutableListOf<PieEntry>()
        sumByCategory.value!!.filter { it.category.isExpence == typeState.value }
            .forEach {
            if (it.amount > 0)
                pieEntries.add(PieEntry(it.amount.toFloat(), it.category.name))
        }
        return pieEntries

//        val pieEntries = mutableListOf<PieEntry>()
//        getSumAmountByCategory().forEach {
//            if (it.amount > 0)
//                pieEntries.add(PieEntry(it.amount.toFloat(), it.category.name))
//        }
//        return pieEntries
    }

//    fun getCategoriesColors() = _operationsByCategory.value?.keys?.map { it.color }?.toList() ?: throw Exception("No colors")
    fun getCategoriesColors() = sumByCategory.value?.map { it.category.color } ?: throw Exception("No colors")

    fun getPeriodFromRange(date: LocalDate, typePeriod: PeriodRange) = periodService.getPeriodFromRange(date, typePeriod)

    override fun onCleared() {
//        operationsService.removeListener(operationListener)
//        periodService.removeListener(periodListener)
        super.onCleared()
        Log.d("L_IFECYCLE_TAG_FF", "onCleared: ")
    }
}