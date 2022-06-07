package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ItemTransactionBinding
import com.onopry.budgetapp.model.services.CategoriesService
import com.onopry.budgetapp.model.services.OperationsService
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.utils.getTextLocalDateDMY
import java.time.LocalDate

interface OperationActionListener{
    fun onOperationDelete(operation: OperationsDto)
    fun onOperationEdit(operation: OperationsDto)
}

class OperationsDuffCallback(
    private val oldList: List<OperationsDto>,
    private val newList: List<OperationsDto>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldPos: Int, newPos: Int) =
        oldList[oldPos] == newList[newPos]
}

class OperationsAdapter(
    private val operationsService: OperationsService,
    private val actionListener: OperationActionListener
): RecyclerView.Adapter<OperationsAdapter.CategoriesViewHolder>(), View.OnClickListener
 {
    var operationList: List<OperationsDto> = emptyList()
        set(newValue){
            val diffCAll = OperationsDuffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCAll)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
//            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)

        binding.transactionEditImg.setOnClickListener(this)
        binding.transactionDeleteImg.setOnClickListener(this)

        return CategoriesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val operation = operationList[position]
        with(holder.binding){
            transactionDeleteImg.tag = operation
            transactionEditImg.tag = operation

            transactionCategoryText.text = operationsService.getCategoryById(operation.categoryId).name
            transactionCategoryImage.setImageResource(operationsService.getCategoryById(operation.categoryId).icon)
            transactionDeleteImg.setImageResource(R.drawable.ic_delete_transaction)
            transactionDate.text = getTextFromDate(operation.date)

            transactionCategoryImage.setBackgroundColor(operationsService.getCategoryById(operation.categoryId).color)

            toolsOperationId.text = operation.id.substring(0..15)

            transactionAmountMoney.text = operation.amount.toString()
            if (operation.isExpence)
                transactionAmountMoney.setTextColor(transactionAmountMoney.resources.getColor(R.color.red))
            else
                transactionAmountMoney.setTextColor(transactionAmountMoney.resources.getColor(R.color.green))
        }


    }

     private fun getTextFromDate(date: LocalDate): String{
         val dayMonthYearTriple = date.getTextLocalDateDMY()
         return "${dayMonthYearTriple.first} ${dayMonthYearTriple.second} ${dayMonthYearTriple.third}"
     }

    override fun getItemCount() = operationList.size

    class CategoriesViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root)

     override fun onClick(v: View?) {
         val operation = v?.tag as OperationsDto
         when(v.id){
             R.id.transaction_delete_img -> actionListener.onOperationDelete(operation)
             R.id.transaction_edit_img -> actionListener.onOperationEdit(operation)
         }
     }
 }