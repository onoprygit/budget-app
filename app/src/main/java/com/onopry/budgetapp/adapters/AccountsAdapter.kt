package com.onopry.budgetapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ItemAccountBinding
import com.onopry.budgetapp.model.dto.AccountDto
import com.onopry.budgetapp.model.dto.CategoriesDto

typealias AccountSelectionListener = (account: AccountDto) -> Unit

class AccountsAdapter (
    private val accountsSelectionListener: AccountSelectionListener
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>(), View.OnClickListener {

    var accounts = emptyList<AccountDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.setOnClickListener(this)
        return AccountViewHolder(binding, false)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        with(holder.binding){
            accountContainer.tag = account
//            accountContainer.setBackgroundColor(Color.parseColor("#E9E7F9"))
            when(account.type) {
                "CARD" -> itemAccCardImageView.setImageResource(R.drawable.ic_credit_card)
                "BANK" -> itemAccCardImageView.setImageResource(R.drawable.ic_bank_building)
                "OTHER" -> itemAccCardImageView.setImageResource(R.drawable.ic_money)
            }
            itemAccAccsName.text = account.name
        }
    }

    override fun getItemCount() = accounts.size

    override fun onClick(v: View) {
        val account = v.tag as AccountDto
        accountsSelectionListener.invoke(account)
    }

    class AccountViewHolder(
        val binding: ItemAccountBinding,
        val isSelected: Boolean
    ): RecyclerView.ViewHolder(binding.root){
    }
}