package com.onopry.budgetapp.views.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.FragmentSingInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSingInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingInBinding.inflate(inflater, container, false)
        return binding.root
    }
}