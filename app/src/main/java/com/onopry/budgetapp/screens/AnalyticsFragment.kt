package com.onopry.budgetapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.databinding.AnalyticsFragmentBinding
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.AnalyticsViewModel
import com.onopry.budgetapp.viewmodels.TransactionsViewModel


class AnalyticsFragment : Fragment() {

    private val viewModel: AnalyticsViewModel by viewModels { startFactory() }
    private lateinit var binding : AnalyticsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false)

        binding.textHome.text = "Фрагмент-хуент \"Аналитика\""
        binding.fabAnalytics.setOnClickListener {
            navigator().showAddOperationScreen()
        }

        return binding.root
    }


    companion object {
        fun newInstance(): AnalyticsFragment {
            val fragment = AnalyticsFragment()
            return fragment
        }
    }
}