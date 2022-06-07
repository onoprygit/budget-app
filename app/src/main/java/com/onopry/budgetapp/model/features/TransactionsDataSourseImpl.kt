package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.dto.OperationsDto
import java.util.*
import kotlin.random.Random


class TransactionsDataSourseImpl: TransactionsDataSourse {
    private var transactionsList = mutableListOf<OperationsDto>()
    override fun getTransactions(): MutableList<OperationsDto>{

        transactionsList = (1..30).map {
            OperationsDto(
                id = UUID.randomUUID().toString(),
                amount = Random.nextInt(-10000,10000),
                categoryId = CATEGORIES[Random.nextInt(0,9)].id,
            )
        }.toMutableList()
        return transactionsList
    }

    override fun deleteTransaction(transaction: OperationsDto) {
        transactionsList.remove(transaction)
    }

    // TODO: Поменять на нормальный kotlin-style код
    override fun editTransaction(transaction: OperationsDto) {
        val transactionListId = transactionsList.indexOf(transaction)
        if (transactionsList[transactionListId] != transaction)
            transactionsList[transactionListId] = transaction

    }

    override fun addTransaction(transaction: OperationsDto) {
        transactionsList.add(transaction)
    }

    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()
    }

}