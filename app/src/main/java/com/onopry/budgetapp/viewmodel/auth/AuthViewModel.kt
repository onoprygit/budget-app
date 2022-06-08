package com.onopry.budgetapp.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.repo.CategoryRepoListener
import com.onopry.budgetapp.model.repo.RealTimeDBRepository
import com.onopry.budgetapp.utils.LogTags
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthViewModel (
    private val categoriesService: CategoriesService,
    private val repository: RealTimeDBRepository
): ViewModel() {
//    private val repository = RealTimeDBRepository(categoriesService)

    private val authRepository = repository.authRepository
//    private val authRepository = AuthRepository()

    val user = authRepository.user

    /*private val _isUserLoggedIn = MutableLiveData<Boolean>(false)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn*/

    val isUserLoggedIn = authRepository.isUserLoggedIn

    val listener: CategoryRepoListener = {
        Log.d(LogTags.GENERATE_DATA_TAG, "load categories size: ${it.size} ")
    }

    init {

    }

    fun generateDefaultUserData(){
        viewModelScope.launch {
            authRepository.initNewUserCategories()
//            runBlocking { repository.initNewUserOperations() }
//            runBlocking { repository.initNewUserTargets() }
        }
    }

    fun isUserLogged() = authRepository.isUserAuth()

    fun signUp(email: String, pass: String){
        Log.d("AUTH_TAG_TEST", "MODEL signUp: ")
        authRepository.signUp(email, pass)
    }

    fun signIn(email: String, pass: String){
        Log.d("AUTH_TAG_TEST", "MODEL signIn: ")
        authRepository.signIn(email, pass)
//        categoryRepository.addListener(listener)
    }

    fun signOut(){
        authRepository.singOut()
    }

    fun isEmailCorrect(email: String) =
        email.contains("@")
                && email.contains(".")
                && email.length >= 5

    fun isPassCorrect(pass: String) = pass.length >= 3

}