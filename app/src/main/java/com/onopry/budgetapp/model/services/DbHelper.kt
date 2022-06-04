package com.onopry.budgetapp.model

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
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DB_ROOT:DatabaseReference
lateinit var CURRENT_USER_UID: String

lateinit var REF_DB_USERS_OPERATIONS: DatabaseReference

/*FirebaseDatabase.getInstance("https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app")*/

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

fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DB_ROOT = FirebaseDatabase.getInstance("https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app").reference
}

fun initNewUserData(){
    val TAG = "INIT_USER_DATA"

    val uid = AUTH.currentUser?.uid.toString()
    val categories = CategoriesModel(CategoryDataSourseImpl()).getCategories()
    categories.forEach{ category ->
        REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).push().updateChildren(category.toMap())
    }

    //TARGETS
    val b = REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).get()
    val a = b.result
    Log.d(TAG, "categories size: ${a.childrenCount}")
    a.children.forEach {
        Log.d(TAG, "${it.key}")
    }
//    Log.d(TAG, "categories size: ${a.children}")
    val targets = listOf(
        TargetDTO(
            id = "720cb4c2-46e6-48e6-8098-87625b866802",
            title = "На машину",
            cost = 700000,
            currentAmount = 0,
            date = LocalDate.of(2022, 6, 20)
        ),
        TargetDTO(
            id = "f5ced627-5fab-41ef-88d8-19599fae79ef",
            title = "На гараж",
            cost = 80000,
            currentAmount = 20000,
            date = LocalDate.of(2022, 7, 12)
        )
    )
    targets.forEach { target ->
        REF_DB_ROOT.child(NODE_TARGETS).child(uid).push().updateChildren(target.toMap())
//        REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).push().updateChildren(CategoriesDto(
//            id = UUID.randomUUID().toString(),
//            name = target.title,
//            icon = R.drawable.ic_category_placeholder,
//            color = R.color.category_other,
//            targetId =
//
//        ))
    }

    val operations = (1..30).map {
        OperationsDto(
            id = UUID.randomUUID().toString(),
            amount = Random.nextInt(100,10000),
            category = categories[Random.nextInt(0,9)],
            date = LocalDate.of(2022, Random.nextInt(4,7), Random.nextInt(8, 25)),
            isExpence = Random.nextBoolean()
        )
    }.sortedByDescending { it.date }.toMutableList()

    operations.forEach{ operation ->
        REF_DB_ROOT.child(NODE_OPERATIONS).child(uid).push().updateChildren(operation.toMap())
    }

}

fun updateTargetsToDb(target: Map<String, Any>, id: String){
    val uid = AUTH.currentUser?.uid.toString()
    REF_DB_ROOT.child(NODE_TARGETS).child(uid).child(id).updateChildren(target)
}

fun updateCategoryToDb(category: Map<String, Any>, id: String){
    val uid = AUTH.currentUser?.uid.toString()
    REF_DB_ROOT.child(NODE_CATEGORIES).child(uid).child(id).updateChildren(category)
}

/*    fun updateFirebaseOperations(operation: Map<String, Any>){
        val uid = AUTH.currentUser?.uid.toString()
        val id = operation[CHILD_ID]

        REF_DB_ROOT.child(DB_USERS).child(operation[CHILD_ID] as String).updateChildren(operation)

    }*/


