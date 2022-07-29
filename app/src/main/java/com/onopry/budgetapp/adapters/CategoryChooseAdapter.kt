package com.onopry.budgetapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.databinding.ItemCategoryBinding
import com.onopry.budgetapp.model.dto.CategoriesDto

typealias CategoryActionListener = (category: CategoriesDto) -> Unit

class CategoryChooseAdapter(
    private val actionListener: CategoryActionListener
): RecyclerView.Adapter<CategoryChooseAdapter.CategoryChooseViewHolder>(), View.OnClickListener{

    var categoryList: List<CategoriesDto> = emptyList()
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryChooseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CategoryChooseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryChooseViewHolder, position: Int) {
        val category = categoryList[position]
        with(holder.binding){
            holder.itemView.tag = category
            transactionCategoryText.text = category.name
            transactionCategoryImage.setImageResource(category.icon)
            transactionCategoryImage.setBackgroundColor(root.resources.getColor(category.color))
        }
    }

    override fun getItemCount() = categoryList.size

    class CategoryChooseViewHolder(
        val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val category = v.tag as CategoriesDto
        actionListener.invoke(category)
    }

}