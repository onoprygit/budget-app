package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.Transactions
import com.onopry.budgetapp.model.TransactionsService
import kotlin.random.Random

class TransactionsDataSourseImpl: TransactionsDataSourse{
    private var transactions = mutableListOf<Transactions>()
    override fun getTransactions(): MutableList<Transactions>{
        transactions = (1..30).map {
            Transactions(
                amount = Random.nextInt(-10000,10000),
                category = CATEGORIES[Random.nextInt(0,9)]
            )
        }.toMutableList()
        return transactions
    }

    companion object {
        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()
    }

}