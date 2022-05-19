package com.onopry.budgetapp.model

import android.util.Log
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import java.util.*
import kotlin.random.Random

// Листенер отдает список транзакций, который будет обновлен после операций
typealias TransactionsListener = (transactions: List<TransactionsDto>) -> Unit // TODO: разобраться поподробнее с typealias и как вообще этот слушатель работает

class TransactionService {

    private var transactionsList = mutableListOf<TransactionsDto>()
    private val listeners = mutableSetOf<TransactionsListener>()

    init {
        transactionsList = (1..30).map {
            TransactionsDto(
                id = UUID.randomUUID(),
                amount = Random.nextInt(-10000,10000),
                category = CATEGORIES[Random.nextInt(0,9)]
            )}.toMutableList()
    }

    fun getTransactionsList(): List<TransactionsDto> = transactionsList

    fun deleteTransaction(transaction: TransactionsDto) {
        val indexToDelete = transactionsList.indexOfFirst { it.id == transaction.id }
        if (indexToDelete != -1) {
            transactionsList.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    /** @param id Идентификатор транзакции */
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
    }

    fun addTransaction(transaction: TransactionsDto) {
        transactionsList.add(transaction)
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