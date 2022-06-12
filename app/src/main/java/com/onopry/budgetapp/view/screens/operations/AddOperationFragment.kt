package com.onopry.budgetapp.view.screens.operations

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.AddOperationFragmentBinding
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.view.screens.DatePickerFragment
import com.onopry.budgetapp.viewmodel.operations.AddingMoneyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

const val TODAY = 0
const val YESTERDAY = 1
const val DAY_BEFORE_YESTERDAY = 2
const val OTHER_DAY = 3

@AndroidEntryPoint
class AddOperationFragment : Fragment() {

    private val viewModel: AddingMoneyViewModel by viewModels()

    private lateinit var binding: AddOperationFragmentBinding
    private lateinit var categoryBottomSheet: BottomSheetDialogFragment
    private lateinit var datePickerFragment: DatePickerFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = AddOperationFragmentBinding.inflate(inflater, container, false)
//        Log.d(CONSTANTS.ADDING_MONEY_FRAGMENT_TAG, "savedInstanceState is empty? BUNDLE IS ${savedInstanceState?.isEmpty}")
//        Log.d(CONSTANTS.ADDING_MONEY_FRAGMENT_TAG, (arguments?.getSerializable("toEdit") as TransactionsDto?)?.id ?: "nonono")

        binding.addingOperationDatePick.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.addingOperationDatePick.tag = LocalDate.now()
        binding.addingOperationEmptyCategoryIc.tag = CategoriesDto(
            id = "no id",
            name = "[Нет категории]",
            icon = R.drawable.ic_category_placeholder
        )

        binding.addingOperationCurrencyIc.setImageResource(R.drawable.ic_ruble)

        // Инициализация модального окна со списком категорий
        categoryBottomSheet = CategoryBottomSheet {
            binding.addingOperationEmptyCategoryIc.setImageResource(it.icon)
            binding.addingOperationEmptyCategoryIc.tag = it
            binding.addingOperationSelectCategory.text = it.name
            binding.addingOperationSelectCategory.setBackgroundColor(it.color)
            binding.addingOperationEmptyCategoryIc.setBackgroundColor(it.color)
            categoryBottomSheet.dismiss()
        }

        // Выбор категории
        binding.addingOperationSelectCategory.setOnClickListener {
            categoryBottomSheet.show(childFragmentManager, null)
        }

        // Добавления операции в список
        binding.addingOperationAddButton.setOnClickListener {
//            //todo: Add validation money input
            with(binding) {
                val operation = OperationsDto(
                    id = "",
                    amount = addingOperationEditText.text.toString().toInt(),
                    category = (addingOperationEmptyCategoryIc.tag as CategoriesDto),
                    isExpence = operationRadioExpence.isChecked,
                    date = addingOperationDatePick.tag as LocalDate
                )
                viewModel.addOperation(operation)
            }
        }

        //Выбор даты
        datePickerFragment = DatePickerFragment { year, _month, day ->
            // todo: переделать или сделать wrapper
            val month = _month + 1
//            if (!isToday(year, month, day)) {
//                binding.addingOperationDatePick.text = "$day-$month-$year"
//                Log.d("RANDOM TAG", "today!")
//            }
            with(binding.addingOperationDatePick) {
                when (isToday(year, month, day)) {
                    TODAY -> text = "Сегодня"
                    YESTERDAY -> text = "Вчера"
                    DAY_BEFORE_YESTERDAY -> text = "Позавчера"
                    OTHER_DAY -> text = "$day-$month-$year"
                }
            }

            Log.d("RANDOM TAG", "$year-$month-$day")
            binding.addingOperationDatePick.tag = LocalDate.of(year, month, day)
        }

        binding.addingOperationDatePick.setOnClickListener {
            datePickerFragment.show(parentFragmentManager, "datePicker")
        }

//        navigator().toast("${Calendar.YEAR} ${Calendar.MONTH} ${Calendar.DAY_OF_MONTH}")


        return binding.root
    }

    companion object {
        fun newInstance(key: String, value: OperationsDto) = AddOperationFragment().apply {
            arguments = Bundle().apply {
                putSerializable(key, value)
            }
        }

        fun newInstance() = AddOperationFragment()
    }

    private fun isToday(year: Int, month: Int, day: Int): Int {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH) + 1
        val d = c.get(Calendar.DAY_OF_MONTH)
        Log.d("DATETAG", "CalendarNow: $y $m $d")
        Log.d("DATETAG", "DateGet: $year $month $day")
        if ((y == year) && (m == month)) {
            if (d == day) return TODAY
            if (d == day + 1) return YESTERDAY
            if (d == day + 2) return DAY_BEFORE_YESTERDAY
        }
        return OTHER_DAY
    }
}
