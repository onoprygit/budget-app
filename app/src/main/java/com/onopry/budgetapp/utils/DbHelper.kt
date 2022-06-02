package com.onopry.budgetapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DB_ROOT:DatabaseReference

/*FirebaseDatabase.getInstance("https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app")*/

const val DB_USERS = "users"

const val NODE_OPERATIONS = "operations"

const val CHILD_ID = "id"
const val CHILD_AMOUNT = "amount"
const val CHILD_CATEGORY = "category"
const val CHILD_DATE = "date"
const val CHILD_IS_EXPENCE = "isExpence"




fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DB_ROOT = FirebaseDatabase.getInstance(
        "https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app").reference
}

