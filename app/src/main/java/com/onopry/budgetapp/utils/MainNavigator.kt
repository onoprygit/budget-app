package com.onopry.budgetapp.utils

import com.onopry.budgetapp.model.dto.TransactionsDto

/**
 * Интерфейс, описывающий навигицию приложения,
 * перемещения между экранами, вызов новых
 */

interface MainNavigator {

    // Основные экраны, отображенные на панели навигации
    fun showAnalyticsScreen()
    fun showTransactionsListScreen()
    fun showBudgetAndDebtsScreen()
    fun showMoreScreen()

    fun goBack()
    fun toast(messageRes: Int)

    // Экраны, вызываемые с основных экранов
    //fun showEditTransactionScreen(transaction: TransactionsDto)

}