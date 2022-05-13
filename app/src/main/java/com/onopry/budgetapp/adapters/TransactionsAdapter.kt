package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ItemTransactionBinding
import com.onopry.budgetapp.model.features.TransactionsDataSourseImpl
import com.onopry.budgetapp.model.features.TransactionsModel

class TransactionsAdapter(

): RecyclerView.Adapter<TransactionsAdapter.CategoriesViewHolder>() {

//    var transactions: List<Transactions> = emptyList()
//        set (newValue){
//            field = newValue
//            notifyDataSetChanged()
//        }
    var transactions = TransactionsModel(TransactionsDataSourseImpl()).getTransactions()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val transaction = transactions[position]
        with(holder.binding){
            categoryText.text = transaction.category.category
            categoryImage.setImageResource(transaction.category.icon)
            if (transaction.amount > 0) {
                amountMoney.text = "+" + transaction.amount.toString()
                amountMoney.setTextColor(amountMoney.resources.getColor(R.color.green))
            } else {
                amountMoney.text = transaction.amount.toString()
                amountMoney.setTextColor(amountMoney.resources.getColor(R.color.red))
            }
        }
    }

    override fun getItemCount() = transactions.size

    class CategoriesViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root)
}