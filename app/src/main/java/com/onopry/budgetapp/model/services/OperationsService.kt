package com.onopry.budgetapp.model.services

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.onopry.budgetapp.model.*
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAccessor
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// Слушатель отдает список транзакций, который будет обновлен после изменения списка операций
typealias OperationsListener = (transactions: List<OperationsDto>) -> Unit

class OperationsService(
    private val targetService: TargetService,
    private val categoriesService: CategoriesService
) {

    private var operationsList/*: MutableList<OperationsDto> */ = mutableListOf<OperationsDto>()
    private var operationsMap = mutableMapOf<String, OperationsDto>()
    private val listeners = mutableSetOf<OperationsListener>()

    init {
//        operationsList = (1..30).map {
//            OperationsDto(
//                id = UUID.randomUUID().toString(),
//                amount = Random.nextInt(100,10000),
//                category = CATEGORIES[Random.nextInt(0,9)],
//                date = LocalDate.of(2022, Random.nextInt(4,7), Random.nextInt(8, 25)),
//                isExpence = Random.nextBoolean()
//            )}.toMutableList()
//        operationsList.sortByDescending { it.date }
//        loadOperations()
    }

    fun loadOperations() {
        val TAG = "ADSASDJUDSOAINDM"
        categoriesService.category_log()
        REF_DB_ROOT.child(NODE_OPERATIONS).child(AUTH.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<OperationsDto>()
                    snapshot.children.forEach {
                        list.add(OperationsDto(
                                id = it.key as String,
                                amount = (it.child(CHILD_OPERATION_AMOUNT).value as Long).toInt(),
                                CategoriesDto(
                                    id = it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_ID).value as String,
                                    name = it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_NAME).value as String,
                                    icon = (it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_ICON).value as Long).toInt(),
                                    color = (it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_COLOR).value as Long).toInt(),
                                    isParent = it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_IS_PARENT).value as Boolean,
                                    isExpence = it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_IS_EXPENCE).value as Boolean,
                                    parentId = it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_PARENT_ID).value as String,
                                    targetId = it.child(CHILD_OPERATION_CATEGORY).child(CHILD_CATEGORY_TARGET_ID).value as String
                                ),
                                date = LocalDate.parse(it.child(CHILD_OPERATION_DATE).value as String),
                                isExpence = it.child(CHILD_OPERATION_IS_EXPENCE).value as Boolean,
                                accountId = it.child(CHILD_OPERATION_ACCOUNT_ID).value as String
                            )
                        )
                    }
                    operationsList = list
                    notifyChanges()
                }

                override fun onCancelled(error: DatabaseError) {
                    throw OperationsUploadCancelledException()
                }
            })
//        notifyChanges()
    }

    fun addListener(listener: OperationsListener) {
        listeners.add(listener)
        listener.invoke(operationsList)
    }

    fun removeListener(listener: OperationsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(operationsList) }
    }

    fun getOperationById(id: String) =
        operationsList.firstOrNull { it.id == id } ?: throw OperationNotFoundException()

    fun deleteOperation(operation: OperationsDto) {
        val indexToDelete = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToDelete != -1) {
            operationsList = ArrayList(operationsList)
            operationsList.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun editOperation(operation: OperationsDto) {
        val indexToEdit = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToEdit != -1) {
            operationsList = ArrayList(operationsList)
            operationsList[indexToEdit] = operation
            operationsList.sortByDescending { it.date }
            notifyChanges()
        } else
            throw OperationIdNotFoundException()
    }

    fun updateDataBaseOperation(operation: OperationsDto){
        val indexToEdit = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToEdit != -1) {
            Log.d("TAG_updateDataBaseOperation", "$operation")
            REF_DB_ROOT.child(NODE_OPERATIONS)
                .child(AUTH.currentUser?.uid.toString())
                .child(operation.id)
                .setValue(operation.toMap())
        } else
            throw OperationIdNotFoundException()
    }

    fun addOperation(operation: OperationsDto) {
        operationsList.add(operation)
        operationsList.sortByDescending { it.date }
        if (operation.category.targetId != null) {
            targetService.addOperationToTarget(operation)
        }

        notifyChanges()
    }




    fun getOperationByPeriod(startDate: LocalDate, endDate: LocalDate) = operationsList
        .filter { operation ->
            operation.date in startDate..endDate
        }

    private fun getSumByPeriod(startDate: LocalDate, endDate: LocalDate): Int {
        var sum = 0
//        Log.d("TAGG", "getSumByPeriod: ${getOperationByPeriod(startDate, endDate).map { Pair(it.amount, it.isExpence) }}")
        getOperationByPeriod(startDate, endDate)
            .forEach {
//                if (it.isExpence) sum -= it.amount else sum += it.amount
                if (it.isExpence) sum += it.amount
            }
        return sum
    }

    fun getSumExpencesByPeriod(period: PeriodDate) =
        getOperationByPeriod(period.startDate, period.finishDate)
            .filter { it.isExpence }
            .sumOf { it.amount }

    /** возвращает список **/
    private fun getSumExpences(operations: List<OperationsDto>) =
        operations
            .filter { it.isExpence }
            .sumOf { it.amount }

    /** возвращает мапу соответствия key - categories, val - List<Operations> **/
    fun extractOperationsByCategoryForPeriod(
        startDate: LocalDate,
        finishDate: LocalDate
    ): Map<CategoriesDto, List<OperationsDto>> {
        val extractedOperationsByCategory = mutableMapOf<CategoriesDto, List<OperationsDto>>()

        // получаем операции за текущий(выбранный) период
        val operationsByPeriod = getOperationByPeriod(startDate, finishDate)

        //получаем сет уникальных категорий из списка выше
        val uniqueCategorySet = mutableSetOf<CategoriesDto>()
        operationsByPeriod.forEach { uniqueCategorySet.add(it.category) }

        uniqueCategorySet.forEach { category ->
            extractedOperationsByCategory[category] =
                operationsByPeriod.filter { operation ->
                    operation.category.id == category.id
                }
        }
        return extractedOperationsByCategory
    }

    /** отдает мапу ключ - категории, значение - суммарные траты по категории **/
    fun getOperationsByCategorySumAmount(categoryMap: Map<CategoriesDto, List<OperationsDto>>): Map<CategoriesDto, Int> {
        val oprsByCatgsSum = mutableMapOf<CategoriesDto, Int>()
        categoryMap.forEach {
            oprsByCatgsSum[it.key] = getSumExpences(it.value)
        }
        return oprsByCatgsSum
    }

    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()
    }

    /*    fun getOperationsByDay(dateOfDay: LocalDate) = operationsList
        .filter { operation ->
            operation.date.dayOfMonth == dateOfDay.dayOfMonth
        }

    fun getOperationsByMonth(dateOfMonth: LocalDate) = operationsList
        .filter { operation ->
            operation.date.month == dateOfMonth.month
        }

    fun getOperationsByYear(dateOfYear: LocalDate) = operationsList
        .filter { operation ->
            operation.date.year == dateOfYear.year
        }*/

    fun naebka() {
        getSumByPeriod(LocalDate.now(), LocalDate.now())
        removeListener { }
    }

}