package com.onopry.budgetapp.view.screens.operations
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onopry.budgetapp.adapters.CategoryActionListener
import com.onopry.budgetapp.adapters.CategoryChooseAdapter
import com.onopry.budgetapp.databinding.CategoryBottomSheetBinding
import com.onopry.budgetapp.viewmodel.operations.CategoryBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint

const val COLUMN_NUMBER = 5

@AndroidEntryPoint
class CategoryBottomSheet(
    private val actionListener: CategoryActionListener
): BottomSheetDialogFragment() {

    private lateinit var binding: CategoryBottomSheetBinding
    private lateinit var adapter: CategoryChooseAdapter
    private val viewModel: CategoryBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoryBottomSheetBinding.inflate(inflater, container, false)

        viewModel.categories.observe(viewLifecycleOwner) {
            adapter.categoryList = it
        }
        adapter = CategoryChooseAdapter(actionListener)

        binding.categoryRecyclerChooser.layoutManager = GridLayoutManager(context, COLUMN_NUMBER)
        binding.categoryRecyclerChooser.adapter = adapter

        return binding.root
    }
}

