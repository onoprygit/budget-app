package com.onopry.budgetapp.viewmodel.screens.auth

import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {

    private fun isEmailCorrect(email: String): Boolean{
        return email.contains('@')
                && email.contains('.')
                && email.length > 3
                && !email.contains(' ')
    }

    private fun isPasswordCorrect(pass: String): Boolean{
        return pass.length > 6
                && !pass.contains(' ')
    }

    fun isFieldsCorrect(email: String, pass: String) = isPasswordCorrect(pass) || isEmailCorrect(email)

}