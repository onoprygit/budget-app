package com.onopry.budgetapp.view.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.onopry.budgetapp.databinding.FragmentSingInBinding
import com.onopry.budgetapp.utils.navigator


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSingInBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingInBinding.inflate(inflater, container, false)

        /*private lateinit var auth: FirebaseAuth*/
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            navigator().toast(auth.currentUser?.uid.toString())
        }


        binding.authTvRegister.setOnClickListener{
            navigator().toast("REGISTER")
            navigator().showSingUpScreen()
        }

        binding.authBtnAuth.setOnClickListener {
            val email = binding.authEtMail.text.toString()
            val password = binding.authEtPass.text.toString()

            if (!isEmailCorrect(email) && !isPasswordCorrect(password)){
                navigator().toast("Неверно введена почта или пароль")
                return@setOnClickListener
            }
        }



        return binding.root
    }

    fun authUser(email: String, pass: String){
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    navigator().toast("Authentication failed, check your email and password or sign up")

                } else {
                    navigator().toast("Success")
                    navigator().setBottomNavVisible(true)
                    navigator().showAnalyticsScreen()
                }
            }
    }

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
}