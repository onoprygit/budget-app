package com.onopry.budgetapp.view.screens.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.onopry.budgetapp.databinding.FragmentSignUpBinding
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        authViewModel.user.observe(viewLifecycleOwner) { user ->
            Log.d("AUTH_TAG_TEST", "onCreateView: ${authViewModel.isUserLogged()}")
            if (user != null) {
                navigator().toast("Добро пожаловать, ${user.email.toString()}")
                navigator().showAnalyticsScreen()
            } else navigator().toast("Войдите или зарегистрируйтесь")
        }

        binding = FragmentSignUpBinding.inflate(inflater,container,false)

        //write
        binding.registrationBtnRegister.setOnClickListener {
            val email = binding.registerEtMail.text.toString()
            val password = binding.registerEtPass.text.toString()

            if (authViewModel.isEmailCorrect(email) && authViewModel.isPassCorrect(password))
                authViewModel.signUp(email, password)
            else
                navigator().toast("Вы ввели что то не так")
        }

        return binding.root
    }
}