package com.onopry.budgetapp.model.services

import android.accounts.Account
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.onopry.budgetapp.model.dto.AccountDto
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.utils.ACCOUNT
import com.onopry.budgetapp.utils.FIREBASE
import com.onopry.budgetapp.utils.LogTags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class AccountsService @Inject constructor(
    private val authRepository: AuthRepository,
    private val operationsService: OperationsService
) {

    private val uid: String = FirebaseAuth.getInstance().currentUser?.uid!!
    private val dbRefRoot = FirebaseDatabase.getInstance(FIREBASE.DATABASE_URL).reference
    private val dbRefAccounts = dbRefRoot.child(ACCOUNT.NODE)

    private val _accounts = MutableLiveData<List<AccountDto>>(listOf())
    val accounts: LiveData<List<AccountDto>> = _accounts

    init {
        load()
    }

    suspend fun generateDefaultUserAccountAsync() = coroutineScope {
        async {
            val uid = authRepository.user.value!!.uid
            var list = mutableListOf<AccountDto>()
            list.add(AccountDto(id = UUID.randomUUID().toString(), name = "ЗП", type = "CARD"))
            list.add(AccountDto(id = UUID.randomUUID().toString(), name = "Вклад", type = "BANK"))
            list.add(AccountDto(id = UUID.randomUUID().toString(), name = "Биткоин", type = "OTHER"))

            dbRefAccounts.child(uid).updateChildren(mapOf<String, Any>(
                list[0].id to list[0].toMap(),
                list[1].id to list[1].toMap(),
                list[2].id to list[2].toMap()
            ))
            list.map { it.id }
        }
    }

    private fun load(){
        dbRefRoot.child(ACCOUNT.NODE).child(authRepository.user.value!!.uid)
            .addValueEventListener( object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<AccountDto>()
                    snapshot.children.mapNotNull { accountSnap ->
                        list.add(AccountDto.parseSnapshot(accountSnap))
                    }
                    _accounts.postValue(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(LogTags.FETCH_DATA_TAG, "Load accounts failed")
                }

            }

        )
    }

    fun getSumOfOperationsByAccounts() = operationsService.getSumOfOperationsByAccounts(_accounts.value!!)

    fun getOperationsByAccounts() = operationsService.getOperationByAccounts(_accounts.value!!)

    suspend fun addAccount(acc: AccountDto){
        val uid = authRepository.user.value!!.uid
        dbRefAccounts.child(uid).updateChildren(mapOf(
            acc.id to acc.toMap()
        ))
    }

    suspend fun deleteAccount(acc: AccountDto){
        val uid = authRepository.user.value!!.uid
        dbRefAccounts.child(uid).child(acc.id).removeValue()
    }

    suspend fun editAccount(acc: AccountDto){
        val uid = authRepository.user.value!!.uid
        dbRefAccounts.child(uid).child(acc.id).setValue(acc.toMap())
    }





    /*

        val uid = authRepository.user.value!!.uid
        val returnedCategoryList = mutableListOf<CategoriesDto>()
        val defCategoriesList = CategoriesModel(CategoryDataSourseImpl()).getCategories()
        Log.d("GENERATE_DATA_TAG", "generateDefaultUserCategories: ${defCategoriesList.size}")
        val map = HashMap<String, Any>()
        defCategoriesList.forEach { category ->
            map["/${category.id}"] = category.toMap()
            returnedCategoryList.add(category)
        }
        dbRef.child(FirebaseHelper.CATEGORIES_KEY).child(uid!!).updateChildren(map)
        return returnedCategoryList

    */
}