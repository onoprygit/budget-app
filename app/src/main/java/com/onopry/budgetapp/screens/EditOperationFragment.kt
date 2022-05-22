@file:Suppress("MoveLambdaOutsideParentheses")

package com.onopry.budgetapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.EditOperationFragmentBinding
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.EditOperationViewModel

class EditOperationFragment : Fragment() {

    private lateinit var binding: EditOperationFragmentBinding
//    private lateinit var viewModel: EditOperationViewModel
    private val viewModel: EditOperationViewModel by viewModels { startFactory() }
    private lateinit var categoryBottomSheet: BottomSheetDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setOperation(requireArguments().getString(ARG_OPERATION_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditOperationFragmentBinding.inflate(inflater, container, false)

        binding.editingOperationTitle.text = viewModel.operation.value?.id ?: "NONE"

        //get actual live data and setting it to view's of this fragment
        viewModel.operation.observe(viewLifecycleOwner, {
            binding.editingOperationEditText.setText(it.amount.toString())
            binding.editingOperationEmptyCategoryIc.setImageResource(it.category.icon)
            binding.editingOperationSelectCategory.text = it.category.name
        })

        // Start bottom sheet to choose category
        binding.editingOperationSelectCategory.setOnClickListener {
            categoryBottomSheet.show(childFragmentManager, null)
        }

        // Set listener of bottom sheet click categories
        categoryBottomSheet = CategoryBottomSheet{
            binding.editingOperationEmptyCategoryIc.setImageResource(it.icon)
            binding.editingOperationEmptyCategoryIc.tag = it
            binding.editingOperationSelectCategory.text = it.name
            categoryBottomSheet.dismiss()
//            navigator().goBack()
        }

        binding.editingOperationSaveButton.setOnClickListener {
            with(binding){
                val operation = TransactionsDto(
                    id = "_",
                    amount = editingOperationEditText.text.toString().toInt(),
                    category = binding.editingOperationEmptyCategoryIc.tag as CategoriesDto
//                    date
                )
                viewModel.editOperation(operation)
            }
            navigator().goBack()
        }

        return binding.root
    }

    companion object {

        private const val ARG_OPERATION_ID = "ARG_OPERATION_ID"

        fun newInstance() = EditOperationFragment()

        fun newInstance(key: String, operationId: String): EditOperationFragment {
            val fragment = EditOperationFragment()
            fragment.arguments = bundleOf(key to operationId)
            return fragment
        }
    }

}