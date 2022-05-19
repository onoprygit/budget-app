package com.onopry.budgetapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.CategoryBottomSheet
import com.onopry.budgetapp.databinding.FragmentAddingMoneyBinding
import com.onopry.budgetapp.viewmodels.AddingMoneyViewModel
import com.onopry.budgetapp.viewmodels.TransactionsViewModel
import com.onopry.budgetapp.viewmodels.startFactory


class AddingMoneyFragment : Fragment() {

//    private val viewModel: AddingMoneyViewModel by viewModels { startFactory() }

    private lateinit var binding: FragmentAddingMoneyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddingMoneyBinding.inflate(inflater, container, false)

        val categoryBottomSheet = CategoryBottomSheet()

        binding.buttonSelectCategory.setOnClickListener {
            categoryBottomSheet.show(parentFragmentManager, null)
        }

        return binding.root
    }
}