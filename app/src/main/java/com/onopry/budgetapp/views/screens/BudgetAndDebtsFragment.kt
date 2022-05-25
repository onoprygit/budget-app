package com.onopry.budgetapp.views.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.databinding.FragmentBudetAndDebtsBinding
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.BudgetAndDebtsViewModel


class BudgetAndDebtsFragment : Fragment() {

    private val viewModel: BudgetAndDebtsViewModel by viewModels { startFactory() }
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

    companion object {
        fun newInstance(): BudgetAndDebtsFragment {
            val fragment = BudgetAndDebtsFragment()
            return fragment
        }
    }
}