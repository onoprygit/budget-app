package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ItemTransactionBinding
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.model.features.TransactionsDataSourseImpl
import com.onopry.budgetapp.model.features.TransactionsModel

interface UserActionListener{
    fun onUserDelete()
    fun onUserEdit()
}

class TransactionsAdapter(
    private val transactionList: MutableList<TransactionsDto>
): RecyclerView.Adapter<TransactionsAdapter.CategoriesViewHolder>(
) {

//    var transactions: List<Transactions> = emptyList()
//        set (newValue){
//            field = newValue
//            notifyDataSetChanged()
//        }
//    var transactions = TransactionsModel(TransactionsDataSourseImpl()).getTransactions()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val transaction = transactionList[position]
        with(holder.binding){
            categoryText.text = transaction.category.category
            categoryImage.setImageResource(transaction.category.icon)
            categoryDeleteImg.setImageResource(R.drawable.ic_delete_transaction)
                if (transaction.amount > 0) {
                amountMoney.text = "+" + transaction.amount.toString()
                amountMoney.setTextColor(amountMoney.resources.getColor(R.color.green))
            } else {
                amountMoney.text = transaction.amount.toString()
                amountMoney.setTextColor(amountMoney.resources.getColor(R.color.red))
            }

            categoryDeleteImg.setOnClickListener {
                deleteTransaction(position)
            }

        }
    }

    override fun getItemCount() = transactionList.size

    private fun deleteTransaction(position: Int){
        val pos = position
        transactionList.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, transactionList.size)
    }

    class CategoriesViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root)
}