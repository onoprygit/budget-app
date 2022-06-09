package com.onopry.budgetapp.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.services.CategoriesListener
import com.onopry.budgetapp.utils.LogTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
//    private val categoriesService: CategoriesService,
    private val authRepository: AuthRepository
): ViewModel() {
//    private val repository = RealTimeDBRepository(categoriesService)

//    private val authRepository = repository.authRepository
//    private val authRepository = AuthRepository()

    val user = authRepository.user

    /*private val _isUserLoggedIn = MutableLiveData<Boolean>(false)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn*/

    val isUserLoggedIn = authRepository.isUserLoggedIn

    val listener: CategoriesListener = {
        Log.d(LogTags.GENERATE_DATA_TAG, "load categories size: ${it.size} ")
    }

    init {
        Log.d(LogTags.DI_INSTANCES_TAG, "AuthViewModel init")

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