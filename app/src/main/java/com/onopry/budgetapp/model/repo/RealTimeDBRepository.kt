package com.onopry.budgetapp.model.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.repo.FirebaseHelper.CATEGORIES_KEY
import com.onopry.budgetapp.model.repo.FirebaseHelper.OPERATIONS_KEY
import com.onopry.budgetapp.model.repo.FirebaseHelper.TARGETS_KEY

import com.onopry.budgetapp.utils.FIREBASE

class RealTimeDBRepository(private val categoriesService: CategoriesService) {
//    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val dbRef = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference

    val authRepository = AuthRepository()
//    val operationsRepository = OperationsRepository()
//    val targetRepository = TargetRepository()
//    val categoryRepository = CategoryRepository()

    suspend fun initNewUserOperations(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        OperationsRepository.generateDefaultUserOperations(dbRef.child(OPERATIONS_KEY).child(uid.toString()))
    }

    suspend fun initNewUserTargets(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        CategoryRepository.generateDefaultUserCategories(dbRef.child(CATEGORIES_KEY).child(uid.toString()), categoriesService)
    }

    suspend fun initNewUserCategories(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        TargetRepository.generateDefaultUserTargets(
            dbRef, uid!!
        )
    }
}

object FirebaseHelper {
    const val CATEGORIES_KEY = "user-categories"
    const val OPERATIONS_KEY = "user-operations"
    const val TARGETS_KEY = "user-targets"
    const val ACCOUNTS_KEY = "user-accounts"
    const val DEBTS_KET = "user-debts"
}