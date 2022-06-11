package com.onopry.budgetapp.view.screens.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.onopry.budgetapp.databinding.FragmentSingInBinding
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSingInBinding

    private val authViewModel: AuthViewModel by activityViewModels()
//    { startFactory() }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingInBinding.inflate(inflater, container, false)

        authViewModel.user.observe(viewLifecycleOwner) { user ->
            Log.d("AUTH_TAG_TEST", "onCreateView: ${authViewModel.isUserLogged()}")
            if (user != null) {
                navigator().toast("Добро пожаловать, ${user.email.toString()}")
//                navigator().showAnalyticsScreen()
                navigator().showOperationsListScreen()
            } else navigator().toast("Войдите или зарегистрируйтесь")
        }



        binding.authTvRegister.setOnClickListener{
            navigator().toast("Зарегистрируетесь здесь")
            navigator().showSingUpScreen()
        }

        binding.authBtnAuth.setOnClickListener {
            val email = binding.authEtMail.text.toString()
            val password = binding.authEtPass.text.toString()

            if (authViewModel.isEmailCorrect(email) && authViewModel.isPassCorrect(password))
                authViewModel.signIn(email, password)
            else
                navigator().toast("Вы ввели что то не так")
        }

        return binding.root
    }
}