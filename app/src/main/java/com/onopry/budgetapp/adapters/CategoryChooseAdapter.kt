package com.onopry.budgetapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.databinding.ItemCategoryBinding
import com.onopry.budgetapp.model.dto.CategoriesDto

class CategoryChooseAdapter(
    val categoryList: List<CategoriesDto>
): RecyclerView.Adapter<CategoryChooseAdapter.CategoryChooseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryChooseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryChooseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryChooseViewHolder, position: Int) {
        with(holder.binding){
            categoryText.text = categoryList[position].category
            categoryImage.setImageResource(categoryList[position].icon)
        }
    }

    override fun getItemCount() = categoryList.size

    class CategoryChooseViewHolder(
        val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root)

}