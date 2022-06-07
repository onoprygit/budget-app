package com.onopry.budgetapp.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.repo.AuthRepository
import com.onopry.budgetapp.model.repo.RealTimeDBRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthViewModel(
    private val categoriesService: CategoriesService
): ViewModel() {

/*    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData(null)
    val user: LiveData<FirebaseUser?> = _user

    init {
        //        isUserLoggedIn.value = false
        if (auth.currentUser != null)
            _user.postValue(auth.currentUser)
    }

    fun isUserLogged() = auth.currentUser != null

    fun signUp(email: String, pass: String): Task<AuthResult> {
        Log.d("AUTH_TAG_TEST", "REPO signUp: ")
        return auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                val result = if (it.isSuccessful) {
                    _user.postValue(auth.currentUser)
                    it
                } else it
            }
    }


    fun signIn(email: String, pass: String): Task<AuthResult> {
        Log.d("AUTH_TAG_TEST", "REPO signIn: ")
        return auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) _user.postValue(auth.currentUser)
            }
    }

    fun singOut(){
        auth.signOut()
        isUserLoggedIn.postValue(false)
    }

    fun isEmailCorrect(email: String) =
        email.contains("@") && email.contains(".") && email.length >= 5

    fun isPassCorrect(pass: String) =
        pass.length >= 3*/
    private val repository = RealTimeDBRepository(categoriesService)

    private val authRepository = repository.authRepository

    val user = repository.authRepository.user

    /*private val _isUserLoggedIn = MutableLiveData<Boolean>(false)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn*/

    val isUserLoggedIn = repository.authRepository.isUserLoggedIn

    fun generateDefaultUserData(){
        viewModelScope.launch {
            runBlocking {
                repository.initNewUserCategories()
            }
            runBlocking {
                repository.initNewUserOperations()
            }
            runBlocking { repository.initNewUserTargets() }
        }
    }

    fun isUserLogged() = repository.authRepository.isUserAuth()

    fun signUp(email: String, pass: String){
        Log.d("AUTH_TAG_TEST", "MODEL signUp: ")
        repository.authRepository.signUp(email, pass)
    }

    fun signIn(email: String, pass: String){
        Log.d("AUTH_TAG_TEST", "MODEL signIn: ")
        repository.authRepository.signIn(email, pass)
    }

    fun signOut(){
        repository.authRepository.singOut()
    }

    fun isEmailCorrect(email: String) =
        email.contains("@") && email.contains(".") && email.length >= 5

    fun isPassCorrect(pass: String) =
        pass.length >= 3

}