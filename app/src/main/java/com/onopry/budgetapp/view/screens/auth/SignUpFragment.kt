package com.onopry.budgetapp.view.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.onopry.budgetapp.databinding.FragmentSignUpBinding
import com.onopry.budgetapp.utils.AUTH
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

            if (!isEmailCorrect(email) && !isPasswordCorrect(password)){
                navigator().toast("Неверно введена почта или пароль")
                return@setOnClickListener
            }
            createUser(email, password)
        }

        return binding.root
    }

    private fun createUser(email: String, pass: String){
        AUTH.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{
                navigator().toast("createUserWithEmail:onComplete: " + it.isSuccessful)
                if (!it.isSuccessful){
                    navigator().toast("SingUp failed." + it.exception)
                } else {
                    navigator().toast("COMPLETE " + it.result)
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