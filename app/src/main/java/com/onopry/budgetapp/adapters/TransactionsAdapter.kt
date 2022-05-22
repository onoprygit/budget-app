package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ItemTransactionBinding
import com.onopry.budgetapp.model.dto.TransactionsDto

interface TransactionActionListener{
    fun onTransactionDelete(transaction: TransactionsDto)
//    fun onTransactionAdd(transaction: TransactionsDto)
    fun onTransactionEdit(transaction: TransactionsDto)
}

class OperationsDuffCallback(
    private val oldList: List<TransactionsDto>,
    private val newList: List<TransactionsDto>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldPos: Int, newPos: Int) =
        oldList[oldPos] == newList[newPos]


}

class TransactionsAdapter(
    private val actionListener: TransactionActionListener
): RecyclerView.Adapter<TransactionsAdapter.CategoriesViewHolder>(), View.OnClickListener
 {
    var transactionList: List<TransactionsDto> = emptyList()
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
        val transaction = transactionList[position]
        with(holder.binding){
            transactionDeleteImg.tag = transaction
            transactionEditImg.tag = transaction

            transactionCategoryText.text = transaction.category.name
            transactionCategoryImage.setImageResource(transaction.category.icon)
            transactionDeleteImg.setImageResource(R.drawable.ic_delete_transaction)
            transactionDate.text = transaction.date.toString()

            //
//            toolsOperationId.text = transaction.id
            //

                if (transaction.amount > 0) {
                transactionAmountMoney.text = "+" + transaction.amount.toString()
                transactionAmountMoney.setTextColor(transactionAmountMoney.resources.getColor(R.color.green))
            } else {
                transactionAmountMoney.text = transaction.amount.toString()
                transactionAmountMoney.setTextColor(transactionAmountMoney.resources.getColor(R.color.red))
            }
        }
    }

    override fun getItemCount() = transactionList.size

    class CategoriesViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root)

     override fun onClick(v: View?) {
         val transaction = v?.tag as TransactionsDto
         when(v.id){
             R.id.transaction_delete_img -> actionListener.onTransactionDelete(transaction)
             R.id.transaction_edit_img -> actionListener.onTransactionEdit(transaction)
         }
     }
 }