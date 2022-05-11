package com.onopry.budgetapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onopry.budgetapp.adapters.CategoryChooseAdapter
import com.onopry.budgetapp.databinding.CategoryBottomSheetBinding
import com.onopry.budgetapp.model.features.CategoriesModel
import com.onopry.budgetapp.model.features.CategoryDataSourseImpl

const val COLUMN_NUMBER = 5

class CategoryBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: CategoryBottomSheetBinding
    private lateinit var adapter: CategoryChooseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoryBottomSheetBinding.inflate(inflater, container, false)

        adapter = CategoryChooseAdapter(CategoriesModel(CategoryDataSourseImpl()).getCategories()/*.toList()*/)
        binding.categoryRecyclerChooser.layoutManager = GridLayoutManager(context, COLUMN_NUMBER)
        binding.categoryRecyclerChooser.adapter = adapter

        return binding.root
    }
}