package com.onopry.budgetapp.model.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.model.dto.AccountDto
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.repo.FirebaseHelper
import com.onopry.budgetapp.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

// Слушатель отдает список транзакций, который будет обновлен после изменения списка операций
typealias OperationsListener = (transactions: List<OperationsDto>) -> Unit

class OperationsService @Inject constructor(
//    private val authRepository: AuthRepository,
    private val targetService: TargetService,
) {
    private val uid: String = FirebaseAuth.getInstance().currentUser?.uid!!
    private val dbRefRoot = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefOperations = dbRefRoot.child(FirebaseHelper.OPERATIONS_KEY).child(uid).ref

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    private val listeners = mutableSetOf<OperationsListener>()

    init {
        _operations.value = emptyList<OperationsDto>()
        load()
    }

    private suspend fun loadLocal(
        categories: List<CategoriesDto>,
        accountsid: List<String>
    ): MutableList<OperationsDto> {
        val incomeCategories = categories.filter { !it.isExpence }
        val expenceCategories = categories.filter { it.isExpence }
        val list = mutableListOf<OperationsDto>()
        (1..15).map {
            list.add(OperationsDto(
                    id = UUID.randomUUID().toString(),
                    amount = Random.nextInt(100, 10000),
                    category = expenceCategories.random(),
                    date = LocalDate.of(2022, Random.nextInt(5, 7), Random.nextInt(8, 18)),
                    isExpence = true,
                    accountId = accountsid.random()
                ))
        }
        (1..5).map {
            list.add(OperationsDto(
                id = UUID.randomUUID().toString(),
                amount = Random.nextInt(10000, 30000),
                category = incomeCategories.random(),
                date = LocalDate.of(2022, Random.nextInt(5, 7), Random.nextInt(8, 18)),
                isExpence = false,
                accountId = accountsid.random()
            ))
        }
        list.shuffle()
        list.sortByDescending { it.date }
        return list
    }

    private fun load() {
        dbRefOperations.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val operations = mutableListOf<OperationsDto>()
                snapshot.children.mapNotNull {
                    operations.add(OperationsDto.parseSnapshot(it))
                }
                _operations.postValue(operations)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(LogTags.FETCH_DATA_TAG, "Fetch operations failed: ${error.message}")
            }
        })

    }

    // Operations self methods
    suspend fun generateDefaultUserOperations(categories: List<CategoriesDto>, accounts: List<String>) =
        coroutineScope {
            var operations = mutableListOf<OperationsDto>()
            operations = loadLocal(categories, accounts)
            val operationMap = mutableMapOf<String, Any>()

            operations.forEach {
                operationMap["/${it.id}"] = it.toMap()
            }

            dbRefOperations.updateChildren(operationMap)
        }

    fun getOperationById(id: String) = _operations.value?.firstOrNull() { it.id == id }
        ?: throw OperationNotFoundException()

    suspend fun deleteOperation(operation: OperationsDto) {
        val indexToDelete = _operations.value?.indexOfFirst { it.id == operation.id }
            ?: throw OperationNotFoundException()
        if (indexToDelete != -1) {
            if (operation.category.targetId.isNotEmpty()) {

                targetService.removeTarget(
                    targetService.getTargetById(
                        operation.category.targetId
                    )
                )

                dbRefOperations.child(operation.id).removeValue().await()
            } else
                dbRefOperations.child(operation.id).removeValue().await()
        }
    }

    fun editOperation(operation: OperationsDto) {
        val indexToEdit = _operations.value?.indexOfFirst { it.id == operation.id }
            ?: throw OperationNotFoundException()
        if (indexToEdit != -1) {
            dbRefOperations.child(operation.id).updateChildren(operation.toMap())
        } else
            throw OperationIdNotFoundException()
    }

    suspend fun addOperation(operation: OperationsDto) {
        dbRefOperations.child(operation.id).setValue(operation.toMap())
        _operations.value?.sortedByDescending { it.date }
        if (operation.category.targetId.isNotEmpty()) {
            targetService.addOperationToTarget(operation)
        }
    }

    //Operations by period

    fun getOperationByPeriod(startDate: LocalDate, endDate: LocalDate) = _operations.value!!
        .filter { operation -> operation.date in startDate..endDate }

    private fun getSumByPeriod(startDate: LocalDate, endDate: LocalDate): Int {
        var sum = 0
        getOperationByPeriod(startDate, endDate)
            .forEach {
                if (it.isExpence) sum += it.amount
            }
        return sum
    }

//    fun getSumExpencesByPeriod(period: PeriodDate, isExpence: Boolean) =
//        getOperationByPeriod(period.startDate, period.finishDate)
//            .filter { it.isExpence == isExpence }
//            .sumOf { it.amount }

    fun getSumExpencesByPeriod(period: PeriodDate, isExpence: Boolean) = _operations.value!!
        .filter { it.date in period.startDate..period.finishDate }
        .filter { it.isExpence == isExpence }
        .sumOf { it.amount }

    private fun getSumExpences(operations: List<OperationsDto>) =
        operations
            .filter { it.isExpence }
            .sumOf { it.amount }

    // Operations by categories
    /** возвращает мапу соответствия key - categories, val - List<Operations> **/
    fun extractOperationsByCategoryForPeriod(
        startDate: LocalDate, finishDate: LocalDate
    ): Map<CategoriesDto, List<OperationsDto>> {
        val extractedOperationsByCategory = mutableMapOf<CategoriesDto, List<OperationsDto>>()

        // получаем операции за текущий(выбранный) период
        val operationsByPeriod = getOperationByPeriod(startDate, finishDate)

        //получаем сет уникальных категорий из списка выше
        val uniqueCategorySet = mutableSetOf<CategoriesDto>()
        operationsByPeriod?.forEach { uniqueCategorySet.add(it.category) }

        uniqueCategorySet.forEach { category ->
            extractedOperationsByCategory[category] =
                operationsByPeriod?.filter { operation ->
                    operation.category.id == category.id
                }!!
        }
        return extractedOperationsByCategory
    }

    /** отдает мапу ключ - категории, значение - суммарные траты по категории **/
    fun getOperationsByCategorySumAmount(
        categoryMap: Map<CategoriesDto, List<OperationsDto>>
    ): Map<CategoriesDto, Int> {
        val oprsByCatgsSum = mutableMapOf<CategoriesDto, Int>()
        categoryMap.forEach {
            oprsByCatgsSum[it.key] = getSumExpences(it.value)
        }
        return oprsByCatgsSum
    }

    // Operations by accounts
    fun getOperationByAccounts(accounts: List<AccountDto>): Map<String, List<OperationsDto>> = mutableMapOf<String, List<OperationsDto>>()
        .apply {
            accounts.forEach { account ->
                this[account.id] = _operations.value!!
                    .filter { operation -> operation.accountId == account.id }
            }
        }

    fun getSumOfOperationsByAccounts(accounts: List<AccountDto>): Map<String, Int> = mutableMapOf<String, Int>()
        .apply {
            getOperationByAccounts(accounts).entries.forEach {
                this[it.key] = it.value
                    .filter { operation -> operation.isExpence }
                    .sumOf { operation -> operation.amount }
            }
        }
}