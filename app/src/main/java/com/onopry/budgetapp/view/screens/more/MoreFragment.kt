package com.onopry.budgetapp.view.screens.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.databinding.FragmentMoreBinding
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.viewmodel.auth.AuthViewModel
import com.onopry.budgetapp.viewmodel.more.MoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private val viewModel: MoreViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentMoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        authViewModel.isUserLoggedIn.observe(requireActivity()){
            if (!it) {
                navigator().toast("Выход из аккаунта")
                navigator().showSingInScreen()
            }
        }

        binding = FragmentMoreBinding.inflate(inflater, container, false)
        binding.userName.text = authViewModel.user.value?.email ?: "Doesn't recognize"
        binding.userMail.text = authViewModel.user.value?.uid ?: "Doesn't recognize"
        binding.moreTextSample.text = "Фрагмент Еще"

//        binding.logoutButton.setOnClickListener {
//            authViewModel.signOut()
//            //navigator().showSingInScreen()
//        }

        return binding.root
    }

    companion object {
        fun newInstance(): MoreFragment {
            val fragment = MoreFragment()
            return fragment
        }
    }
}