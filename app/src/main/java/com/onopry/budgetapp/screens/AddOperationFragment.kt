package com.onopry.budgetapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.AddOperationFragmentBinding
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.AddingMoneyViewModel

class AddOperationFragment : Fragment() {

    private val viewModel: AddingMoneyViewModel by viewModels { startFactory() }

    private lateinit var binding: AddOperationFragmentBinding
    private lateinit var categoryBottomSheet:BottomSheetDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddOperationFragmentBinding.inflate(inflater, container, false)
//        Log.d(CONSTANTS.ADDING_MONEY_FRAGMENT_TAG, "savedInstanceState is empty? BUNDLE IS ${savedInstanceState?.isEmpty}")
//        Log.d(CONSTANTS.ADDING_MONEY_FRAGMENT_TAG, (arguments?.getSerializable("toEdit") as TransactionsDto?)?.id ?: "nonono")



        binding.addingOperationCurrencyIc.setImageResource(R.drawable.ic_ruble)

        // Categories chooser
        categoryBottomSheet = CategoryBottomSheet{
            binding.addingOperationEmptyCategoryIc.setImageResource(it.icon)
            binding.addingOperationEmptyCategoryIc.tag = it
            binding.addingOperationSelectCategory.text = it.name
            categoryBottomSheet.dismiss()
//            navigator().goBack()
            }

        binding.addingOperationSelectCategory.setOnClickListener {
            categoryBottomSheet.show(childFragmentManager, null)
        }

        binding.addingOperationAddButton.setOnClickListener {
//            //todo: Add validation money input
//            val category = binding.addingOperationEmptyCategoryIc.tag as CategoriesDto
//            val money = binding.addingOperationEditText.text.toString().toInt()
//            viewModel.addTransaction(money, category)
//            navigator().goBack()
            with(binding){
                val operation = TransactionsDto(
                    id = "",
                    amount = addingOperationEditText.text.toString().toInt(),
                    category = binding.addingOperationEmptyCategoryIc.tag as CategoriesDto
//                    date
                )
                viewModel.addTransaction(operation)
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance(key: String, value: TransactionsDto) = AddOperationFragment().apply {
            arguments = Bundle().apply {
                putSerializable(key, value)
            }
        }

        fun newInstance() = AddOperationFragment()
    }
}