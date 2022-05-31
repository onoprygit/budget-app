package com.onopry.budgetapp.utils

/**
 * Интерфейс, описывающий навигицию приложения,
 * перемещения между экранами, вызов новых
 */

interface MainNavigator {

    // Системное поведение
    fun goBack()
    fun toast(messageRes: String)

    //Экраны регистрации и авторизации
    fun showRegistrationScreen()
    fun showAuthScreen()

    // Основные экраны, отображенные на панели навигации
    fun showAnalyticsScreen()
    fun showOperationsListScreen()
    fun showBudgetAndDebtsScreen()
    fun showMoreScreen()

    // Экраны, вызываемые с основных экранов
    fun showAddOperationScreen()
    fun showEditOperationScreen(operationId: String)


}