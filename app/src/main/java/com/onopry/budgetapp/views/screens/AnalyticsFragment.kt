package com.onopry.budgetapp.views.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.onopry.budgetapp.R
import com.onopry.budgetapp.adapters.AmountByCategoryAdapter
import com.onopry.budgetapp.databinding.AnalyticsFragmentBinding
import com.onopry.budgetapp.utils.*
import com.onopry.budgetapp.viewmodels.AnalyticsViewModel
import java.time.LocalDate

class AnalyticsFragment : Fragment() {

    private val viewModel: AnalyticsViewModel by viewModels { startFactory() }
    private lateinit var binding: AnalyticsFragmentBinding
    private val categoriesAdapter = AmountByCategoryAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AnalyticsFragmentBinding.inflate(inflater, container, false)

        val textDatePair = LocalDate.now().getTextLocalDateMY()
        binding.analyticsMainAmountDate.text = "${textDatePair.first} ${textDatePair.second}"

        //инициалиация для ресайклера
        binding.afCategoryRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.afCategoryRecycler.adapter = categoriesAdapter

        // Следим за периодом
        viewModel.period.observe(viewLifecycleOwner) {
            setTextDate(it)
            binding.analyticsMainAmount.text = "₽ " + viewModel.getAmountByPeriod()

            Log.d("HUITA", "period = ${viewModel.period.value}\n")
        }

        viewModel.operationsByCategory.observe(viewLifecycleOwner){
            categoriesAdapter.categoryList = viewModel.getSumAmountByCategory()
            makePieChart()
        }

        binding.analyticsMainAmountDate.setOnClickListener {
            showPeriodChooseMenu(it)
            Log.d("showPeriodChooseMenu", "MONTH")
        }

        binding.fabAnalytics.setOnClickListener {
            navigator().showAddOperationScreen()
        }

        return binding.root
    }

    private fun showPeriodChooseMenu(v: View) {
        val popupMenu = PopupMenu(requireContext(), v)
        popupMenu.inflate(R.menu.choose_period_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.period_month -> {
                    Log.d("ChooseButtonTAG", "listener showPeriodChooseMenu = MONTH")
                    viewModel.setPeriod(LocalDate.now(), PeriodRange.MONTH)
                    navigator().toast("Месяц")
                    true
                }
                R.id.period_year -> {
                    Log.d("ChooseButtonTAG", "listener showPeriodChooseMenu = YEAR")
                    viewModel.setPeriod(LocalDate.now(), PeriodRange.YEAR)
                    navigator().toast("Год")
                    true
                }
                R.id.period_week -> {
                    Log.d("ChooseButtonTAG", "listener showPeriodChooseMenu = WEEK")
//                    viewModel.setPeriod(getMonthPeriodFromNow(LocalDate.now()))
                    navigator().toast("ПОПРАВИТЬ, не реализовано")
                    true
                }
                R.id.period_other -> {
                    Log.d("ChooseButtonTAG", "listener showPeriodChooseMenu = OTHER")
//                    viewModel.setPeriod(LocalDate.now(), PeriodRange.OTHER)
                    navigator().toast("Календарь")
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    @SuppressLint("SetTextI18n")
    private fun setTextDate(date_: PeriodDate) {
        val date = Triple(date_.startDate, date_.finishDate, date_.periodRange)
        when (date.third) {
            PeriodRange.MONTH -> {
                val textDatePair = date.first.getTextLocalDateMY()
                binding.analyticsMainAmountDate.text = "${textDatePair.first} ${textDatePair.second}"
            }
            PeriodRange.WEEK -> {
                val textDatePair = date.first.getTextLocalDateMY()
                binding.analyticsMainAmountDate.text = "Неделя"
            }
            PeriodRange.YEAR -> {
                val textDatePair = date.first.getTextLocalDateMY()
                binding.analyticsMainAmountDate.text = "${textDatePair.second} год"
            }
            PeriodRange.OTHER -> {
                val textStartDateTriple = date.first.getTextLocalDateDMY()
                val textFinishDateTriple = date.second.getTextLocalDateDMY()
                // dd mm yy - dd mm yy
                binding.analyticsMainAmountDate.text =
                    "${textStartDateTriple.first} ${textStartDateTriple.second} ${textStartDateTriple.third} - " +
                            "${textFinishDateTriple.first} ${textFinishDateTriple.second} ${textFinishDateTriple.third}"
            }
        }
    }

    private fun setupPieChart() {
        with(binding.analCategoriesPieChart){
            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(10f)
            setDrawEntryLabels(false)

            setEntryLabelColor(Color.BLACK)
            centerText = "Spending by Category"
            setCenterTextSize(24f)
            description.isEnabled = false
            extraBottomOffset = 20f
            extraLeftOffset = 20f
            extraRightOffset = 20f
            extraTopOffset = 20f

            with(legend){
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                isEnabled = true
                isWordWrapEnabled = true
            }
        }
    }

    private fun createPie(){
        val pieData = PieData(
            PieDataSet(viewModel.getPieEntriesList(), null).apply {
                colors = viewModel.getCategoriesColors()
                valueFormatter = PercentFormatter(binding.analCategoriesPieChart)
                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            })
        with(binding.analCategoriesPieChart){
            data = pieData
            data.setValueTextSize(14f)
            invalidate()
        }
    }

    private fun makePieChart(){
        setupPieChart()
        createPie()
    }


    companion object {
        fun newInstance(): AnalyticsFragment {
            val fragment = AnalyticsFragment()
            return fragment
        }
    }
}