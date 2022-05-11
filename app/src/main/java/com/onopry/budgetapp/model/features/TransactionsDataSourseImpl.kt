package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.model.dto.TransactionsDto
import kotlin.random.Random

class TransactionsDataSourseImpl: TransactionsDataSourse{
    private var transactions = mutableListOf<TransactionsDto>()
    override fun getTransactions(): MutableList<TransactionsDto>{
        transactions = (1..30).map {
            TransactionsDto(
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