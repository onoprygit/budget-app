package com.onopry.budgetapp.view.screens.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onopry.budgetapp.databinding.FragmentSignUpBinding
import com.onopry.budgetapp.model.AUTH
import com.onopry.budgetapp.model.initNewUserData
import com.onopry.budgetapp.utils.navigator

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)

        //write
        binding.registrationBtnRegister.setOnClickListener {
            val email = binding.registerEtMail.text.toString()
            val password = binding.registerEtPass.text.toString()

            if (!isFieldsCorrect(email, password)) return@setOnClickListener

            createUser(email, password)

        }

        binding.registerEtMail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.registerWarning.visibility = View.GONE
        }

        binding.registerEtPass.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.registerWarning.visibility = View.GONE
        }

        return binding.root
    }

    private fun createUser(email: String, pass: String): Boolean{
        var isSuccess: Boolean = false
        AUTH.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{
                isSuccess = it.isSuccessful
                navigator().toast("createUserWithEmail:onComplete: $isSuccess")
                if (!isSuccess){
                    binding.registerWarning.text = "Ошибка регистрации,\n попробуйте позже"
                    binding.registerWarning.visibility = View.VISIBLE
                    Log.d("REGISER_CODE_TAG","SingUp failed." + it.exception)
                } else {
                    initNewUserData()
                    navigator().showAnalyticsScreen()
                }
            }
        return isSuccess
    }

    private fun isEmailCorrect(email: String): Boolean{
        return email.contains('@')
                && email.contains('.')
                && email.length > 3
                && !email.contains(' ')
    }

    private fun isPasswordCorrect(pass: String): Boolean{
        return pass.length > 5
                && !pass.contains(' ')
    }

    private fun isFieldsCorrect(email: String, password: String):Boolean{
        if (!isEmailCorrect(email)){
            binding.registerWarning.text = "Email должен содержать\n '@',точку и пробел\nПароль должен быть >5"
            binding.registerWarning.visibility = View.VISIBLE
            return false
        }
        if (!isPasswordCorrect(password)){
            binding.registerWarning.text = "Введите данные"
            binding.registerWarning.visibility = View.VISIBLE
            return false
        }
        return true
    }

}