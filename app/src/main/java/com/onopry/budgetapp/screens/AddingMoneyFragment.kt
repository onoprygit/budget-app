package com.onopry.budgetapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onopry.budgetapp.CategoryBottomSheet
import com.onopry.budgetapp.databinding.FragmentAddingMoneyBinding


class AddingMoneyFragment : Fragment() {

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