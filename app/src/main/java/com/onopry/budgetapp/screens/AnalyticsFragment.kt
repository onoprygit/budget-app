package com.onopry.budgetapp.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.onopry.budgetapp.databinding.AnalyticsFragmentBinding
import com.onopry.budgetapp.utils.CONSTANTS
import com.onopry.budgetapp.utils.getTextLocalDate
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.AnalyticsViewModel
import java.time.LocalDate


class AnalyticsFragment : Fragment() {

    private val viewModel: AnalyticsViewModel by viewModels { startFactory() }
    private lateinit var binding : AnalyticsFragmentBinding
    private lateinit var currentDate: LocalDate

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false)

        viewModel.date.observe(viewLifecycleOwner, Observer { date ->
            val textDate = date.getTextLocalDate()
            binding.analyticsMainAmountDate.text = "${textDate.second} ${textDate.first}"
        })

        binding.analyticsMainAmount.text = "₽ " + viewModel.getSumByPeriod(
            startDate = LocalDate.of(2022,5, 1),
            finishDate = LocalDate.of(2022,5, 31)
        )

//        viewModel.operations.observe(viewLifecycleOwner, Observer { operations ->
//            binding.analyticsMainAmount.text = "\u20BD " + viewModel.getSumByPeriod(
//
//            )
//        })


        binding.analyticsMainAmountDate.text = LocalDate.now().toString()

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