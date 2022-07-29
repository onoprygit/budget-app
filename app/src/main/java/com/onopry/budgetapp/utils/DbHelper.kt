package com.onopry.budgetapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DB_ROOT:DatabaseReference

/*FirebaseDatabase.getInstance("https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app")*/

object CATEGORY {
    const val NODE = "user-categories"
    const val ID = "id"
    const val NAME = "name"
    const val ICON = "icon"
    const val COLOR = "color"
    const val IS_PARENT = "isParent"
    const val IS_EXPENCE = "isExpence"
    const val PARENT_ID = "parentId"
    const val TARGET_ID = "targetId"
}

object OPERATION {
    const val NODE = "user-operations"
    const val ID = "id"
    const val AMOUNT = "amount"
    const val CATEGORY_PARENT = "parent"
    const val CATEGORY_CHILD = "child"
    const val CATEGORIES = "categories"
    const val DATE = "date"
    const val IS_EXPENCE = "isExpence"
    const val ACCOUNT_ID = "accountId"
    const val DESCRIPTION = "description"
}

object TARGET {
    const val NODE = "user-targets"
    const val COMPLETED_NODE = "user-targets-completed"
    const val ID = "id"
    const val TITLE = "title"
    const val COST = "cost"
    const val CURRENT_AMOUNT = "currentAmount"
    const val DATE = "date"
    const val DESCRIPTION = "description"
    const val IS_DONE = "isDone"
}

object USER {
    const val NODE = "users"
    const val ID = "id"
    const val MAIL = "mail"
    const val NAME = "name"
    const val SURNAME = "surname"
}

object ACCOUNT {
    const val NODE = "user-accounts"
    const val ID = "id"
    const val NAME = "name"
    const val CURRENCY = "currency"
    const val ICON = "icon"
    const val COLOR = "color"
    const val TYPE = "type"
}


fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DB_ROOT = FirebaseDatabase.getInstance(
        "https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app").reference
}

