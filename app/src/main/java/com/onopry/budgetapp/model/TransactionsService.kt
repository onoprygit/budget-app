package com.onopry.budgetapp.model

import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import kotlin.random.Random

typealias TransactionsListener = (transactions: List<TransactionsDto>) -> Unit

class TransactionsService {
    val transactions = mutableListOf<TransactionsDto>()

    private val listeners = mutableSetOf<TransactionsListener>()

    init {
        //val categories = CategoriesModel(CategoryDataSourseImpl()).getCategories()
        val transactions = (1..30).map {
            TransactionsDto(
                amount = Random.nextInt(10,10000),
                category = CATEGORIES[Random.nextInt(0,9)]
            )
        }.toMutableList()

        fun getTransactions() = transactions

        fun deleteTransactions(transaction: TransactionsDto){
            transactions.remove(transaction)
            notifyChanges()
        }
    }

    fun addListener(listener: TransactionsListener){
        listeners.add(listener)
        listener.invoke(transactions)
    }

    fun deleteListener(listener: TransactionsListener){
        listeners.remove(listener)
        notifyChanges()
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(transactions)}
    }

    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()
        fun getTransList() =  (1..30).map {
            TransactionsDto(
                amount = Random.nextInt(10,10000),
                category = CATEGORIES[Random.nextInt(0,9)]
            )
        }.toMutableList()
    }
}
object TransactionsList {

}