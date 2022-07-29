package com.onopry.budgetapp.view.screens.more

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.R
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
//    private val chaneNameFragment = ChaneNameFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoreBinding.inflate(inflater, container, false)
//        binding.userName.text = authViewModel.user.value?.email ?: "Doesn't recognize"
//        binding.userMail.text = authViewModel.user.value?.uid ?: "Doesn't recognize"

        binding.userName.text = "testexample@test.ex"
        binding.userMail.text = "UserName"

//        binding.logoutButton.setOnClickListener {
//            authViewModel.signOut()
//            //navigator().showSingInScreen()
//        }

        binding.moreSettingsTemplate1.setOnClickListener {
//            chaneNameFragment.show(childFragmentManager, "CHANGE_NAME")

        }

        return binding.root
    }

    companion object {
        fun newInstance(): MoreFragment {
            val fragment = MoreFragment()
            return fragment
        }
    }
}