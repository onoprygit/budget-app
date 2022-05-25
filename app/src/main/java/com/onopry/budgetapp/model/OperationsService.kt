package com.onopry.budgetapp.model

import android.util.Log
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.OperationIdNotFoundException
import com.onopry.budgetapp.utils.OperationNotFoundException
import java.time.LocalDate
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// Листенер отдает список транзакций, который будет обновлен после операций
typealias OperationsListener = (transactions: List<OperationsDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает
//interface TransactionsListener{
//    fun event(transactions: List<TransactionsDto>)
//}

class OperationsService {

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
        notifyChanges()
    }

    fun getOperationsByDay(dateOfDay: LocalDate) = operationsList
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
        }

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
                if (it.isExpence) sum -= it.amount else sum += it.amount
            }
        return sum
    }

    fun getSumExpencesByPeriod(startDate: LocalDate, endDate: LocalDate) =
        getOperationByPeriod(startDate, endDate)
            .filter{ it.isExpence }
            .sumOf { it.amount }


    fun addListener(listener: OperationsListener){
        listeners.add(listener)
        listener.invoke(operationsList)
    }

    fun removeListener(listener: OperationsListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(operationsList) } }



    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()

    }

}