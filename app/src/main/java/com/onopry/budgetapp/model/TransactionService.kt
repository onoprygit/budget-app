package com.onopry.budgetapp.model

import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.OperationIdNotFoundException
import com.onopry.budgetapp.utils.OperationNotFoundException
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// Листенер отдает список транзакций, который будет обновлен после операций
typealias TransactionsListener = (transactions: List<TransactionsDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает
//interface TransactionsListener{
//    fun event(transactions: List<TransactionsDto>)
//}

class TransactionService {

    private var transactionsList = mutableListOf<TransactionsDto>()
    private val listeners = mutableSetOf<TransactionsListener>()

    init {
        transactionsList = (1..30).map {
            TransactionsDto(
                id = UUID.randomUUID().toString(),
                amount = Random.nextInt(100,10000),
                category = CATEGORIES[Random.nextInt(0,9)],
                date = LocalDate.of(2022, Random.nextInt(1,4), Random.nextInt(1, 10)),
                isExpence = Random.nextBoolean()
            )}.toMutableList()
        transactionsList.sortByDescending { it.date }
        }

    fun getTransactionsList(): List<TransactionsDto> = transactionsList

    fun getOperationById(id: String)= transactionsList.firstOrNull { it.id == id } ?: throw OperationNotFoundException()

    fun deleteTransaction(transaction: TransactionsDto) {
        val indexToDelete = transactionsList.indexOfFirst { it.id == transaction.id }
        if (indexToDelete != -1) {
            transactionsList = ArrayList(transactionsList)
            transactionsList.removeAt(indexToDelete)
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


    fun editTransaction(transaction: TransactionsDto){
        val indexToEdit = transactionsList.indexOfFirst { it.id == transaction.id }
        if (indexToEdit != -1) {
            transactionsList = ArrayList(transactionsList)
            transactionsList[indexToEdit] = transaction
            transactionsList.sortByDescending { it.date }
            notifyChanges()
        }
        else
            throw OperationIdNotFoundException()
    }

    fun addTransaction(transaction: TransactionsDto) {
        transactionsList.add(transaction)
        transactionsList.sortByDescending { it.date }
        notifyChanges()
    }

    fun addListener(listener: TransactionsListener){
        listeners.add(listener)
        listener.invoke(transactionsList)
    }

    fun removeListener(listener: TransactionsListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(transactionsList) } }



    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()

    }

}