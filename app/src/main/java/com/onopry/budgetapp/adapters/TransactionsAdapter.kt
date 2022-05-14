package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ItemTransactionBinding
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.model.features.TransactionsDataSourseImpl
import com.onopry.budgetapp.model.features.TransactionsModel

interface TransactionActionListener{
    fun onTransactionDelete(transaction: TransactionsDto)
    fun onTransactionAdd(transaction: TransactionsDto)
    fun onTransactionEdit(transaction: TransactionsDto)
}

class TransactionsAdapter(
    private val actionListener: TransactionActionListener
): RecyclerView.Adapter<TransactionsAdapter.CategoriesViewHolder>(), View.OnClickListener
 {
    var transactionList = emptyList<TransactionsDto>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

//    var transactions: List<Transactions> = emptyList()
//        set (newValue){
//            field = newValue
//            notifyDataSetChanged()
//        }
//    var transactions = TransactionsModel(TransactionsDataSourseImpl()).getTransactions()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)

        binding.transactionDeleteImg.setOnClickListener(this)
        binding.transactionEditImg.setOnClickListener(this)

        return CategoriesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val transaction = transactionList[position]
        with(holder.binding){
            transactionDeleteImg.tag = transaction
            transactionEditImg.tag = transaction

            transactionCategoryText.text = transaction.category.category
            transactionCategoryImage.setImageResource(transaction.category.icon)
            transactionDeleteImg.setImageResource(R.drawable.ic_delete_transaction)
                if (transaction.amount > 0) {
                transactionAmountMoney.text = "+" + transaction.amount.toString()
                transactionAmountMoney.setTextColor(transactionAmountMoney.resources.getColor(R.color.green))
            } else {
                transactionAmountMoney.text = transaction.amount.toString()
                transactionAmountMoney.setTextColor(transactionAmountMoney.resources.getColor(R.color.red))
            }

//            transactionDeleteImg.setOnClickListener {
//                deleteTransaction(position)
//            }

        }
    }

    override fun getItemCount() = transactionList.size

//    private fun deleteTransaction(position: Int){
//        val pos = position
//        transactionList.removeAt(pos)
//        notifyItemRemoved(pos)
//        notifyItemRangeChanged(pos, transactionList.size)
//    }

    class CategoriesViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root)

     override fun onClick(v: View?) {
         val transaction = v?.tag as TransactionsDto
         when(v.id){
             R.id.transaction_delete_img -> actionListener.onTransactionEdit(transaction)
             R.id.transaction_edit_img -> actionListener.onTransactionEdit(transaction)
         }
     }
 }