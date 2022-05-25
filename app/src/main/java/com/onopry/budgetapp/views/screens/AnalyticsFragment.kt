package com.onopry.budgetapp.views.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.AnalyticsFragmentBinding
import com.onopry.budgetapp.utils.getTextLocalDateTriple
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.AnalyticsViewModel
import com.onopry.budgetapp.views.customviews.AmountByCategoryItem
import java.time.LocalDate


class AnalyticsFragment : Fragment() {

    private val viewModel: AnalyticsViewModel by viewModels { startFactory() }
    private lateinit var binding : AnalyticsFragmentBinding
    private lateinit var currentDate: LocalDate
    private lateinit var viewList: MutableList<View>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false)

        ///
        val cView = inflater.inflate(R.layout.item_amount_by_category, null) as ConstraintLayout
        binding.analyticsCategoryLl.addView(cView)
        ///
        val itemView = AmountByCategoryItem(requireContext(), )




        viewModel.date.observe(viewLifecycleOwner, Observer { date ->
            val textDate = date.getTextLocalDateTriple()
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