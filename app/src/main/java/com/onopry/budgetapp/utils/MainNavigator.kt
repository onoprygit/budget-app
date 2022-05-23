package com.onopry.budgetapp.utils

import com.onopry.budgetapp.model.dto.TransactionsDto

/**
 * Интерфейс, описывающий навигицию приложения,
 * перемещения между экранами, вызов новых
 */

interface MainNavigator {

    // Системное поведение
    fun goBack()
    fun toast(messageRes: String)

    // Основные экраны, отображенные на панели навигации
    fun showAnalyticsScreen()
    fun showTransactionsListScreen()
    fun showBudgetAndDebtsScreen()
    fun showMoreScreen()

    // Экраны, вызываемые с основных экранов
    fun showAddOperationScreen()
    fun showEditOperationScreen(operationId: String)


}