package com.onopry.budgetapp.model.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.repo.FirebaseHelper
import com.onopry.budgetapp.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// Слушатель отдает список транзакций, который будет обновлен после изменения списка операций
typealias OperationsListener = (transactions: List<OperationsDto>) -> Unit

class OperationsService(
//    private val authRepository: AuthRepository,
    private val targetService: TargetService,
    private val categoriesService: CategoriesService
) {
    private val uid: String = FirebaseAuth.getInstance().currentUser?.uid!!
    private val dbRefRoot = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefOperations = dbRefRoot.child(FirebaseHelper.OPERATIONS_KEY).child(uid).ref

    private var operationsList = mutableListOf<OperationsDto>()

    private val _operations = MutableLiveData<List<OperationsDto>>()
    val operations: LiveData<List<OperationsDto>> = _operations

    private val listeners = mutableSetOf<OperationsListener>()

    init {
        Log.d(LogTags.DI_INSTANCES_TAG, "OperationsService init")
        operationsList = loadLocal_1(categoriesService.getCategoriesList())
        load()
        }

/*    private fun loadLocal(categories: List<CategoriesDto>) = (1..10).map {
        Log.d(TAG, "loadLocal: ")
            OperationsDto(
                id = UUID.randomUUID().toString(),
                amount = Random.nextInt(100,10000),
                category = categories[Random.nextInt(0,9)],
                date = LocalDate.of(2022, Random.nextInt(4,7), Random.nextInt(8, 25)),
                isExpence = Random.nextBoolean()
            )}.sortedByDescending { it.date }.toMutableList()*/

    private fun loadLocal_1(categories: List<CategoriesDto>): MutableList<OperationsDto> {
//        Log.d("COROUTINES_CATEGORY_TAG", "oper_loadLocal: start")
        val list = mutableListOf<OperationsDto>()
        (1..10).map {
            list.add(
                OperationsDto(
                    id = UUID.randomUUID().toString(),
                    amount = Random.nextInt(100, 10000),
                    category = categories[Random.nextInt(0, 9)],
                    date = LocalDate.of(2022, Random.nextInt(4, 7), Random.nextInt(8, 25)),
                    isExpence = Random.nextBoolean()
                )
            )
        }
        list.sortByDescending { it.date }
        return list
    }

    private suspend fun loadLocal_2(categories: List<CategoriesDto>): MutableList<OperationsDto> {
        Log.d("COROUTINES_CATEGORY_TAG", "oper_loadLocal: start")
        val list = mutableListOf<OperationsDto>()
        (1..10).map {
            list.add(
                OperationsDto(
                    id = UUID.randomUUID().toString(),
                    amount = Random.nextInt(100, 10000),
                    category = categories[Random.nextInt(0, 9)],
                    date = LocalDate.of(2022, Random.nextInt(4, 7), Random.nextInt(8, 25)),
                    isExpence = Random.nextBoolean()
                )
            )
            Log.d("COROUTINES_CATEGORY_TAG", "oper_loadLocal: size: ${list.size}")
        }
        list.sortByDescending { it.date }
        Log.d("COROUTINES_CATEGORY_TAG", "oper_loadLocal: end")
        return list
    }

    private fun load(){
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

    //New user data generation -start-

    suspend fun generateDefaultUserOperations(categories: List<CategoriesDto>) = coroutineScope {
        Log.d("COROUTINES_CATEGORY_TAG", "generateDefaultUserOperations: start")
        //val categories = categoriesService.loadSingleCategories()
        var operations = mutableListOf<OperationsDto>()
        operations = loadLocal_2(categories)

        Log.d("COROUTINES_CATEGORY_TAG", "operations before await ${operations.size}")
        val operationMap = mutableMapOf<String, Any>()

        Log.d("COROUTINES_CATEGORY_TAG", "operations after await ${operations.size}")
        operations.forEach {
            operationMap["/${it.id}"] = it.toMap()
        }

        dbRefOperations.updateChildren(operationMap)
        Log.d("COROUTINES_CATEGORY_TAG", "generateDefaultUserOperations: start")
    }

//            lateinit var categories: List<CategoriesDto>






    /*
        suspend fun generateDefaultUserCategories(refCategories: DatabaseReference) {
//            val defCategoriesList = categoriesService.getCategoriesList()
        val defCategoriesList = CategoriesModel(CategoryDataSourseImpl()).getCategories()
        Log.d("GENERATE_DATA_TAG", "generateDefaultUserCategories: ${defCategoriesList.size}")
        val map = HashMap<String, Any>()
        defCategoriesList.forEach { category ->
            map["/${category.id}"] = category.toMap()
        }
        refCategories.updateChildren(map)
    }
    */

    //New user data generation -end-

    fun addListener(listener: OperationsListener){
        listeners.add(listener)
        listener.invoke(operationsList)
    }

    fun removeListener(listener: OperationsListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(operationsList) } }

    fun getOperationById(id: String) =
        _operations.value?.firstOrNull() { it.id == id } ?: throw OperationNotFoundException()
        // old RAM version
        /*operationsList.firstOrNull { it.id == id } ?: throw OperationNotFoundException()*/

    fun deleteOperation(operation: OperationsDto) {
        //RAM version
        /*val indexToDelete = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToDelete != -1) {
            operationsList = ArrayList(operationsList)
            operationsList.removeAt(indexToDelete)
            notifyChanges()
        }*/

        //FireBase version
        val indexToDelete = _operations.value?.indexOfFirst { it.id == operation.id } ?: throw OperationNotFoundException()
        if (indexToDelete != -1){
            dbRefOperations.child(operation.id).removeValue()
        }
    }

    fun editOperation(operation: OperationsDto){
        //New Firebase
        val indexToEdit = _operations.value?.indexOfFirst { it.id == operation.id } ?: throw OperationNotFoundException()
        if (indexToEdit != -1) {
            dbRefOperations.child(operation.id).updateChildren(operation.toMap())
        }
        else
            throw OperationIdNotFoundException()

        //Old RAM
        /*val indexToEdit = operationsList.indexOfFirst { it.id == operation.id }
        if (indexToEdit != -1) {
            operationsList = ArrayList(operationsList)
            operationsList[indexToEdit] = operation
            operationsList.sortByDescending { it.date }
            notifyChanges()
        }
        else
            throw OperationIdNotFoundException()*/
    }

    suspend fun addOperation(operation: OperationsDto){
        // new Firevase version
        dbRefOperations.child(operation.id).setValue(operation.toMap())
        _operations.value?.sortedByDescending { it.date }
        if (operation.category.targetId.isNotEmpty()){
            targetService.addOperationToTarget(operation)
        }


        // old RAM version
        /*operationsList.add(operation)
        operationsList.sortByDescending { it.date }
        if (operation.category.targetId.isNotEmpty()){
            targetService.addOperationToTarget(operation)
        }
        notifyChanges()*/
    }

/*    suspend fun addOperationFirebase(operation: OperationsDto){
        val id = operation.id
        dbRefOperations.child(id).setValue(operation.toMap())
    }*/

    fun getOperationByPeriod(startDate: LocalDate, endDate: LocalDate) = operationsList
        .filter { operation ->
            operation.date in startDate..endDate
        }

    private fun getSumByPeriod(startDate: LocalDate, endDate: LocalDate): Int{
        var sum = 0
//        Log.d("TAGG", "getSumByPeriod: ${getOperationByPeriod(startDate, endDate).map { Pair(it.amount, it.isExpence) }}")
        getOperationByPeriod(startDate, endDate)
            .forEach{
//                if (it.isExpence) sum -= it.amount else sum += it.amount
                if (it.isExpence) sum += it.amount
            }
        return sum
    }

    fun getSumExpencesByPeriod(period: PeriodDate) =
        getOperationByPeriod(period.startDate, period.finishDate)
            .filter{ it.isExpence }
            .sumOf { it.amount }

    /** возвращает список **/
    private fun getSumExpences(operations: List<OperationsDto>) =
        operations
            .filter{ it.isExpence }
            .sumOf { it.amount }

    /** возвращает мапу соответствия key - categories, val - List<Operations> **/
    fun extractOperationsByCategoryForPeriod(startDate: LocalDate, finishDate: LocalDate): Map<CategoriesDto, List<OperationsDto>> {
        val extractedOperationsByCategory = mutableMapOf<CategoriesDto, List<OperationsDto>>()

        // получаем операции за текущий(выбранный) период
        val operationsByPeriod = getOperationByPeriod(startDate, finishDate)

        //получаем сет уникальных категорий из списка выше
        val uniqueCategorySet = mutableSetOf<CategoriesDto>()
        operationsByPeriod.forEach { uniqueCategorySet.add(it.category) }

        uniqueCategorySet.forEach { category ->
            extractedOperationsByCategory[category] =
                operationsByPeriod.filter { operation ->
                    operation.category.id == category.id
                }
        }
        return extractedOperationsByCategory
    }

    /** отдает мапу ключ - категории, значение - суммарные траты по категории **/
    fun getOperationsByCategorySumAmount(categoryMap: Map<CategoriesDto, List<OperationsDto>>): Map<CategoriesDto, Int> {
        val oprsByCatgsSum = mutableMapOf<CategoriesDto, Int>()
        categoryMap.forEach {
            oprsByCatgsSum[it.key] = getSumExpences(it.value)
        }
        return oprsByCatgsSum
    }

    companion object {
//        private val CATEGORIES = CategoriesModel(CategoryDataSourseImpl()).getCategories()
        fun CATEGORIES(categoriesService: CategoriesService) =
            categoriesService.getCategoriesList()
    }

    /*    fun getOperationsByDay(dateOfDay: LocalDate) = operationsList
        .filter { operation ->
            operation.date.dayOfMonth == dateOfDay.dayOfMonth
        }

    fun getOperationsByMonth(dateOfMonth: LocalDate) = operationsList
        .filter { operation ->
            operation.date.month == dateOfMonth.month
        }

    fun getOperationsByYear(dateOfYear: LocalDate) = operationsList
        .filter { operation ->
            operation.date.year == dateOfYear.year
        }*/

    fun naebka(){
        getSumByPeriod(LocalDate.now(), LocalDate.now())
        removeListener {  }
    }

}