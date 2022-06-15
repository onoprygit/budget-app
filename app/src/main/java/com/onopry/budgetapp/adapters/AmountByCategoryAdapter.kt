package com.onopry.budgetapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.databinding.ItemAmountByCategoryBinding
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.utils.AmountByCategory

typealias AmountByCategoryActionListener = (category: CategoriesDto) -> Unit

class AmountCategoriesDuffCallback(
    private val oldList: List<AmountByCategory>,
    private val newList: List<AmountByCategory>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].category.id == newList[newItemPosition].category.id

    override fun areContentsTheSame(oldPos: Int, newPos: Int) =
        oldList[oldPos] == newList[newPos]
}

class AmountByCategoryAdapter: RecyclerView.Adapter<AmountByCategoryAdapter.AmountByCategoryViewHolder>()
{
    var categoryList: List<AmountByCategory> = emptyList()
        set(newValue) {
            val diffCAll = AmountCategoriesDuffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCAll)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmountByCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAmountByCategoryBinding.inflate(inflater, parent, false)
        return AmountByCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AmountByCategoryViewHolder, position: Int) {
        val elCategory = categoryList[position]
        with(holder.binding){
            amountByCategoryCategoryImage.setImageResource(elCategory.category.icon)
            amountByCategoryCategoryImage.setBackgroundColor(this.root.resources.getColor(elCategory.category.color))
            amountByCategoryCategoryName.text = elCategory.category.name
            amountByCategoryCategoryAmount.text = "${elCategory.amount}₽"
        }
    }

    override fun getItemCount() = categoryList.size

    class AmountByCategoryViewHolder(
        val binding: ItemAmountByCategoryBinding
    ): RecyclerView.ViewHolder(binding.root)
}