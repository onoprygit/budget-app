package com.onopry.budgetapp.model

import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.OperationIdNotFoundException
import com.onopry.budgetapp.utils.OperationNotFoundException
import com.onopry.budgetapp.utils.PeriodDate
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// Листенер отдает список транзакций, который будет обновлен после операций
typealias OperationsListener = (transactions: List<OperationsDto>) -> Unit

class OperationsService(
    private val targetService: TargetService
) {

    private var operationsList = mutableListOf<OperationsDto>()
    private val listeners = mutableSetOf<OperationsListener>()

    init {

        operationsList = (1..30).map {
            OperationsDto(
                id = UUID.randomUUID().toString(),
                amount = Random.nextInt(100,10000),
                category = CATEGORIES[Random.nextInt(0,9)],
                date = LocalDate.of(2022, Random.nextInt(4,6), Random.nextInt(8, 25)),
                isExpence = Random.nextBoolean()
            )}.toMutableList()
        operationsList.sortByDescending { it.date }
        }

    fun addListener(listener: OperationsListener){
        listeners.add(listener)
        listener.invoke(operationsList)
    }

    fun removeListener(listener: OperationsListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(operationsList) } }

    fun getTransactionsList(): List<OperationsDto> = operationsList

    fun getOperationById(id: String)= operationsList.firstOrNull { it.id == id } ?: throw OperationNotFoundException()

    fun deleteOperation(operation: OperationsDto) {
        val indexToDelete = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToDelete != -1) {
            operationsList = ArrayList(operationsList)
            operationsList.removeAt(indexToDelete)
            notifyChanges()
        }
    }

/*    *//** @param id Идентификатор транзакции *//*
    fun editTransaction(oldTransaction: TransactionsDto, id: Int,
                        newAmount:Int, newCategory: CategoriesDto
    ) {
        val indexToEdit = transactionsList.indexOfFirst { it.id == oldTransaction.id }
        if (indexToEdit != -1) {
            with(transactionsList[indexToEdit]) {
                amount = newAmount
                category = newCategory
                notifyChanges()
            }
        }
    }*/


    fun editOperation(operation: OperationsDto){
        val indexToEdit = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToEdit != -1) {
            operationsList = ArrayList(operationsList)
            operationsList[indexToEdit] = operation
            operationsList.sortByDescending { it.date }
            notifyChanges()
        }
        else
            throw OperationIdNotFoundException()
    }

    fun addOperation(operation: OperationsDto) {
        operationsList.add(operation)
        operationsList.sortByDescending { it.date }
        if (operation.category.targetId != null){
            targetService.addOperationToTarget(operation)
        }

        notifyChanges()
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

    fun getOperationByPeriod(startDate: LocalDate, endDate: LocalDate) = operationsList
        .filter { operation ->
            operation.date in startDate..endDate
        }

    //todo: можно сделать красивее
    fun getSumByPeriod(startDate: LocalDate, endDate: LocalDate): Int{
        var sum = 0
//        Log.d("TAGG", "getSumByPeriod: ${getOperationByPeriod(startDate, endDate).map { Pair(it.amount, it.isExpence) }}")
        getOperationByPeriod(startDate, endDate)
            .forEach{
//                if (it.isExpence) sum -= it.amount else sum += it.amount
                if (it.isExpence) sum += it.amount
            }
        return sum
    }

    fun getSumExpencesByPeriod(period: PeriodDate) =
        getOperationByPeriod(period.startDate, period.finishDate)
            .filter{ it.isExpence }
            .sumOf { it.amount }


    //=============================================

    /** возвращает список **/
    fun getSumExpences(operations: List<OperationsDto>) =
        operations
            .filter{ it.isExpence }
            .sumOf { it.amount }

    /** возвращает мапу соответствия key - categories, val - List<Operations> **/
    fun extractOperationsByCategoryForPeriod(startDate: LocalDate, finishDate: LocalDate): Map<CategoriesDto, List<OperationsDto>> {
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
//        private val CATEGORIES: List<CategoriesDto>

//        private val CATEGORIES: MutableList<CategoriesDto>

    }

}