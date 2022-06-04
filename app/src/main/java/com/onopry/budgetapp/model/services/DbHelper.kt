package com.onopry.budgetapp.model.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl
import com.onopry.budgetapp.utils.TargetNotFoundException
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DB_ROOT = FirebaseDatabase.getInstance("https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app").reference
}

fun initNewUserData(){
    val TAG = "COROUTONES_TEST_INIT_TAG"
    val uid = AUTH.currentUser?.uid.toString()
    Log.d(TAG, "in initNewUserData")
    //TODO: такой говнокодище - БООООЖЕ
    GlobalScope.launch(Dispatchers.IO){
        Log.d(TAG, "in GlobalScope start")
        runBlocking {
            Log.d(TAG, "in block CATEGORIES start")
            initNewUserCategories()
            Log.d(TAG, "in block CATEGORIES finish")
        }
        runBlocking {
            Log.d(TAG, "in block OPERATIONS start")
            initNewUserOperations()
            Log.d(TAG, "in block OPERATIONS finish")
        }
        runBlocking {
            Log.d(TAG, "in block TARGETS start")
            initNewUserTargets()
            Log.d(TAG, "in block TARGETS finish")
        }
        Log.d(TAG, "in GlobalScope finish")
    }
}

suspend fun initNewUserCategories(){
    val TAG = "INIT_USER_DATA"

    val uid = AUTH.currentUser?.uid.toString()
    val categories = CategoriesModel(CategoryDataSourseImpl()).getCategoriesFirebase()
    categories.forEach{ category ->
        CoroutineScope(Dispatchers.IO).launch {
            addCategory(category, uid)
        }
    }
}

suspend fun initNewUserTargets(){
    val TAG = "INIT_USER_DATA"
    val uid = AUTH.currentUser?.uid.toString()
    val targets = listOf(
        TargetDTO(
            id = "",
            title = "На машину",
            cost = 700000,
            currentAmount = 0,
            date = LocalDate.of(2022, 6, 20)
        ),
        TargetDTO(
            id = "",
            title = "На гараж",
            cost = 80000,
            currentAmount = 20000,
            date = LocalDate.of(2022, 7, 12)
        )
    )
    var targetId: String
    targets.forEach {
/*        CoroutineScope(Dispatchers.IO).launch {
            addTarget(it, uid)
        }*/
        addTarget(it, uid)
    }
}

suspend fun initNewUserOperations(){
    val categories = CategoriesModel(CategoryDataSourseImpl()).getCategoriesFirebase()
    val uid = AUTH.currentUser?.uid.toString()
    REF_DB_ROOT.child(NODE_CATEGORIES).child(uid)
    val operations = (1..30).map {
        OperationsDto(
            id = UUID.randomUUID().toString(),
            amount = Random.nextInt(100,10000),
            category = categories[Random.nextInt(0,9)],
            date = LocalDate.of(2022, Random.nextInt(4,7), Random.nextInt(8, 25)),
            isExpence = Random.nextBoolean()
        )
    }.sortedByDescending { it.date }.toMutableList()

    operations.forEach{
        /*CoroutineScope(Dispatchers.IO).launch {
            addOperation(it, uid)
        }*/
        addOperation(it, uid)
    }
}

suspend fun addTarget(target: TargetDTO, uid: String) {

        val targetKey = REF_DB_ROOT.child(NODE_TARGETS).child(uid).push().key
        val categorKey = REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).push().key
        if (targetKey == null) {
            throw TargetNotFoundException()
            return
        }

        val categoryOfTarget = CategoriesDto(
            id = "",
            name = target.title,
            icon = R.drawable.ic_category_placeholder,
            targetId = targetKey
        )

        val tarToUpdate = hashMapOf<String, Any>(
            "/$NODE_TARGETS/$uid/$targetKey" to target.toMap(),
            "/$NODE_CATEGORIES/$uid/$categorKey" to categoryOfTarget.toMap()
        )

        REF_DB_ROOT.updateChildren(tarToUpdate).await()
}

suspend fun addOperation(operation: OperationsDto, uid: String){
    val TAG = "OPERATION_TAG_TEST"
    //val operationKey = REF_DB_ROOT.child(NODE_OPERATIONS).child(uid).key
    //Log.d(TAG, "operation key: $operationKey")
    Log.d(TAG, "UID: $uid")
    val categories = REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).get().await()
    val categoriesIdList = mutableListOf<String>()
    categories.children.forEach {
        categoriesIdList.add(it.key as String)
    }

    val newOperation = OperationsDto(
        id = "",
        amount = operation.amount,
        category = operation.category,
        date = operation.date,
        isExpence = operation.isExpence,
        accountId = operation.accountId,
        categoryId = categoriesIdList.random()
    )

    REF_DB_ROOT.child(NODE_OPERATIONS).child(uid).push().updateChildren(newOperation.toMapFire()).await()
}

suspend fun addCategory(category: CategoriesDto, uid: String){
    REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).push().updateChildren(category.toMap()).await()
}



fun updateTargetsToDb(target: Map<String, Any>, id: String){
    val uid = AUTH.currentUser?.uid.toString()
    REF_DB_ROOT.child(NODE_TARGETS).child(uid).child(id).updateChildren(target)
}

fun updateCategoryToDb(category: Map<String, Any>, id: String){
    val uid = AUTH.currentUser?.uid.toString()
    REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).child(id).updateChildren(category)
}

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DB_ROOT:DatabaseReference
lateinit var CURRENT_USER_UID: String

lateinit var REF_DB_USERS_OPERATIONS: DatabaseReference

const val DB_USERS = "users"

const val NODE_OPERATIONS = "users-operations"
const val NODE_TARGETS = "users-targets"
const val NODE_CATEGORIES = "user-categories"
const val NODE_ACCOUNTS = "users-accounts"

const val CHILD_ACCOUNT_ID = "id"
const val CHILD_ACCOUNT_NAME = "name"
const val CHILD_ACCOUNT_CURRENCY = "currency"
const val CHILD_ACCOUNT_ICON = "icon"
const val CHILD_ACCOUNT_COLOR = "color"

const val CHILD_OPERATION_ID = "id"
const val CHILD_OPERATION_AMOUNT = "amount"
const val CHILD_OPERATION_CATEGORY = "category"
const val CHILD_OPERATION_CATEGORY_ID = "categoryId"
const val CHILD_OPERATION_DATE = "date"
const val CHILD_OPERATION_IS_EXPENCE = "isExpence"
const val CHILD_OPERATION_ACCOUNT_ID = "accountId"

const val CHILD_CATEGORY_ID = "id"
const val CHILD_CATEGORY_NAME = "name"
const val CHILD_CATEGORY_ICON = "icon"
const val CHILD_CATEGORY_COLOR = "color"
const val CHILD_CATEGORY_IS_PARENT = "isParent"
//const val CHILD_CATEGORY_IS_PARENT = "isParent"
const val CHILD_CATEGORY_IS_EXPENCE = "isExpence"
const val CHILD_CATEGORY_PARENT_ID = "parentId"
const val CHILD_CATEGORY_TARGET_ID = "targetId"

const val CHILD_TARGET_ID = "id"
const val CHILD_TARGET_TITLE = "title"
const val CHILD_TARGET_COST = "cost"
const val CHILD_TARGET_CURRENT_AMOUNT = "currentAmount"
const val CHILD_TARGET_DATE = "date"
const val CHILD_TARGET_DESRIPTION = "description"
const val CHILD_TARGET_IS_DONE = "isDone"


/*    fun updateFirebaseOperations(operation: Map<String, Any>){
        val uid = AUTH.currentUser?.uid.toString()
        val id = operation[CHILD_ID]

        REF_DB_ROOT.child(DB_USERS).child(operation[CHILD_ID] as String).updateChildren(operation)

    }*/


