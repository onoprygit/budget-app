package com.onopry.budgetapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.databinding.FragmentAnalyticsBinding
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.AnalyticsViewModel
import com.onopry.budgetapp.viewmodels.TransactionsViewModel


class AnalyticsFragment : Fragment() {

    private val viewModel: AnalyticsViewModel by viewModels { startFactory() }
    private lateinit var binding : FragmentAnalyticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false)

        binding.textHome.text = "Фрагмент-хуент \"Аналитика\""
        binding.fabAnalytics.setOnClickListener { Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show() }

        return binding.root
    }


    companion object {
        fun newInstance(): AnalyticsFragment {
            val fragment = AnalyticsFragment()
            return fragment
        }
    }
}