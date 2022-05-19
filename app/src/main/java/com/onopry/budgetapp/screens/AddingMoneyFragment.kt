package com.onopry.budgetapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.FragmentAddingMoneyBinding
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.AddingMoneyViewModel




class AddingMoneyFragment() : Fragment() {

    private val viewModel: AddingMoneyViewModel by viewModels { startFactory() }

    private lateinit var binding: FragmentAddingMoneyBinding
    private lateinit var categoryBottomSheet:BottomSheetDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddingMoneyBinding.inflate(inflater, container, false)

        binding.addingMoneyCurrencyIc.setImageResource(R.drawable.ic_ruble)

        //todo: сделать нормальную переиспользуемую передачу данных при попытке изменить транзакцию
        if (savedInstanceState != null) {
            val transaction = savedInstanceState.getSerializable("transactionTag") as TransactionsDto
            binding.editText.setText(transaction.amount.toString())
            binding.addingMoneyEmptyCategoryIc.setImageResource(transaction.category.icon)
            binding.addingMoneyEmptyCategoryIc.tag = transaction.category
        }

        // Categories chooser
        categoryBottomSheet = CategoryBottomSheet{
            binding.addingMoneyEmptyCategoryIc.setImageResource(it.icon)
            binding.addingMoneyEmptyCategoryIc.tag = it
            categoryBottomSheet.dismiss()
            }

        binding.buttonSelectCategory.setOnClickListener {
            categoryBottomSheet.show(childFragmentManager, null)
        }

        binding.addButton.setOnClickListener {
            //todo: Add validation money input
            val category = binding.addingMoneyEmptyCategoryIc.tag as CategoriesDto
            val money = binding.editText.text.toString().toInt()
            viewModel.addTransaction(money, category)
        }


        return binding.root
    }

    companion object {
        fun newInstance(key:String, transaction: TransactionsDto): AddingMoneyFragment {
            val fragment = AddingMoneyFragment()
            fragment.arguments?.putSerializable(key, transaction)
            return fragment
        }
    }
}