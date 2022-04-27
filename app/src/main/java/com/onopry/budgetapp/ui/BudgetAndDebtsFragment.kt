package com.onopry.budgetapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onopry.budgetapp.databinding.FragmentBudetAndDebtsBinding

class BudgetAndDebtsFragment : Fragment() {

    private lateinit var binding: FragmentBudetAndDebtsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudetAndDebtsBinding.inflate(inflater, container, false)

        binding.textBudgetAndDebts.text = "Бюджет Фрагмент"
        return binding.root
    }
}