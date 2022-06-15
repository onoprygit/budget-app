package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.databinding.ItemMaxOperationsBinding
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.dto.getChildCategory


class MaxAmountOfOperationsAdapterDuffCallback(
    private val oldList: List<OperationsDto>,
    private val newList: List<OperationsDto>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldPos: Int, newPos: Int) = oldList[oldPos] == newList[newPos]
}

class MaxAmountOfOperationsAdapter:
    RecyclerView.Adapter<MaxAmountOfOperationsAdapter.MaxAmountOfOperationsViewHolder>() {

    var operationsList: List<OperationsDto> = emptyList()
        set(newValue) {
            val diffCAll = MaxAmountOfOperationsAdapterDuffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCAll)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaxAmountOfOperationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMaxOperationsBinding.inflate(inflater, parent, false)
        return MaxAmountOfOperationsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MaxAmountOfOperationsViewHolder, position: Int) {
        val operation = operationsList[position]
        with(holder.binding) {
            maxAmountOperationsCategoryImage.setImageResource(operation.categories.getChildCategory().icon)
            maxAmountOperationsCategoryName.text = operation.categories.getChildCategory().name
            maxAmountOperationsCategoryAmount.text = "${operation.categories.getChildCategory()}₽"
        }
    }

    override fun getItemCount() = operationsList.size


    class MaxAmountOfOperationsViewHolder(
        val binding: ItemMaxOperationsBinding
    ): RecyclerView.ViewHolder(binding.root)
}