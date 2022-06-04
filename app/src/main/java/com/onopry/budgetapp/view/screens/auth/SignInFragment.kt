package com.onopry.budgetapp.view.screens.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.onopry.budgetapp.databinding.FragmentSingInBinding
import com.onopry.budgetapp.model.AUTH
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.viewmodel.screens.auth.SignInViewModel


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSingInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingInBinding.inflate(inflater, container, false)

        binding.authTvRegister.setOnClickListener{
            navigator().showSingUpScreen()
        }

        binding.authBtnAuth.setOnClickListener {
            val email = binding.authEtMail.text.toString()
            val password = binding.authEtPass.text.toString()

            if (viewModel.isFieldsCorrect(email, password)){
                binding.authNoUserTv.text = "Ошибка ввода почты\n или пароля"
                binding.authNoUserTv.visibility = View.VISIBLE
                return@setOnClickListener
            }
            authUser(email, password)
        }

        binding.authEtMail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.authNoUserTv.visibility = View.GONE
        }

        binding.authEtPass.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.authNoUserTv.visibility = View.GONE
        }


        return binding.root
    }

    private fun authUser(email: String, pass: String){
        AUTH.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) {

                    Snackbar.make(binding.authEnterTitle, "Такого пользователя не существует", Snackbar.LENGTH_LONG)
                        .setAction("Зарегистрироваться") {
                            navigator().showSingUpScreen()
                        }.show()

                    binding.authNoUserTv.text = "Такого пользователя\n не существует!"
                    binding.authNoUserTv.visibility = View.VISIBLE

                    Log.d("SignInFragment_TAG", "authUser: ${it.exception?.message}")

                } else {
                    navigator().toast("Success")
                    navigator().showAnalyticsScreen()
                }
            }
    }
}