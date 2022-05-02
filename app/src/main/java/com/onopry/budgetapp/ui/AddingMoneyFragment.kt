package com.onopry.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ActivityMainBinding.inflate
import com.onopry.budgetapp.databinding.FragmentAddingMoneyBinding
import com.onopry.budgetapp.databinding.FragmentMoreBinding

class AddingMoneyFragment : Fragment() {

    private lateinit var binding: FragmentAddingMoneyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddingMoneyBinding.inflate(inflater, container, false)



        return binding.root
    }
}