package com.onopry.budgetapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.onopry.budgetapp.model.repo.AuthRepository

class AuthViewModel: ViewModel() {

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


    private val repository = AuthRepository()

    val user = repository.user

    /*private val _isUserLoggedIn = MutableLiveData<Boolean>(false)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn*/

    val isUserLoggedIn = repository.isUserLoggedIn

    fun isUserLogged() = repository.isUserAuth()

    fun signUp(email: String, pass: String){
        Log.d("AUTH_TAG_TEST", "MODEL signUp: ")
        repository.signUp(email, pass)
    }

    fun signIn(email: String, pass: String){
        Log.d("AUTH_TAG_TEST", "MODEL signIn: ")
        repository.signIn(email, pass)
    }

    fun signOut(){
        repository.singOut()
    }

    fun isEmailCorrect(email: String) =
        email.contains("@") && email.contains(".") && email.length >= 5

    fun isPassCorrect(pass: String) =
        pass.length >= 3

}