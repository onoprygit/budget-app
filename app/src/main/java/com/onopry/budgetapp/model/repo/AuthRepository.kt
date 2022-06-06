package com.onopry.budgetapp.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(
) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    /** **/
    private val _isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData(null)
    val user: LiveData<FirebaseUser?> = _user

    init {
        //        isUserLoggedIn.value = false
        if (auth.currentUser != null)
            _user.postValue(auth.currentUser)
    }

    fun isUserAuth() = auth.currentUser != null

    fun signUp(email: String, pass: String): Task<AuthResult> {
        Log.d("AUTH_TAG_TEST", "REPO signUp: ")
        return auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _user.postValue(auth.currentUser)
                    _isUserLoggedIn.postValue(true)
                } else {
                    Log.d("AUTH_TAG", "REPO signUp: Registration failed: ${it.exception?.message}")
                }
            }
    }


    fun signIn(email: String, pass: String): Task<AuthResult> {
        Log.d("AUTH_TAG_TEST", "REPO signIn: ")
        return auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _user.postValue(auth.currentUser)
                    _isUserLoggedIn.postValue(true)
                }
            }
    }

    fun singOut(){
        auth.signOut()
        _isUserLoggedIn.postValue(false)
    }
}