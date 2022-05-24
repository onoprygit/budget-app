package com.onopry.budgetapp.model

import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.OperationIdNotFoundException
import com.onopry.budgetapp.utils.OperationNotFoundException
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// Листенер отдает список транзакций, который будет обновлен после операций
typealias OperationsListener = (transactions: List<OperationsDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает
//interface TransactionsListener{
//    fun event(transactions: List<TransactionsDto>)
//}

class OperationsService {

    private var OperationsList = mutableListOf<OperationsDto>()
    private val listeners = mutableSetOf<OperationsListener>()

    init {
        OperationsList = (1..30).map {
            OperationsDto(
                id = UUID.randomUUID().toString(),
                amount = Random.nextInt(100,10000),
                category = CATEGORIES[Random.nextInt(0,9)],
                date = LocalDate.of(2022, Random.nextInt(1,4), Random.nextInt(1, 10)),
                isExpence = Random.nextBoolean()
            )}.toMutableList()
        OperationsList.sortByDescending { it.date }
        }

    fun getTransactionsList(): List<OperationsDto> = OperationsList

    fun getOperationById(id: String)= OperationsList.firstOrNull { it.id == id } ?: throw OperationNotFoundException()

    fun deleteOperation(operation: OperationsDto) {
        val indexToDelete = OperationsList.indexOfFirst { it.id == operation.id }
        if (indexToDelete != -1) {
            OperationsList = ArrayList(OperationsList)
            OperationsList.removeAt(indexToDelete)
            notifyChanges()
        }
    }

//    /** @param id Идентификатор транзакции */
//    fun editTransaction(oldTransaction: TransactionsDto, id: Int,
//                        newAmount:Int, newCategory: CategoriesDto
//    ) {
//        val indexToEdit = transactionsList.indexOfFirst { it.id == oldTransaction.id }
//        if (indexToEdit != -1) {
//            with(transactionsList[indexToEdit]) {
//                amount = newAmount
//                category = newCategory
//                notifyChanges()
//            }
//        }
//    }


    fun editOperation(operation: OperationsDto){
        val indexToEdit = OperationsList.indexOfFirst { it.id == operation.id }
        if (indexToEdit != -1) {
            OperationsList = ArrayList(OperationsList)
            OperationsList[indexToEdit] = operation
            OperationsList.sortByDescending { it.date }
            notifyChanges()
        }
        else
            throw OperationIdNotFoundException()
    }

    fun addOperation(operation: OperationsDto) {
        OperationsList.add(operation)
        OperationsList.sortByDescending { it.date }
        notifyChanges()
    }

    fun addListener(listener: OperationsListener){
        listeners.add(listener)
        listener.invoke(OperationsList)
    }

    fun removeListener(listener: OperationsListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(OperationsList) } }



    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()

    }

}