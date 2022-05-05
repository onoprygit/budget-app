package com.onopry.budgetapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.TransactionsAdapter
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.FragmentTransactionsBinding
import com.onopry.budgetapp.model.TransactionsList
import com.onopry.budgetapp.model.TransactionsService
import com.onopry.budgetapp.model.features.TransactionsDataSourseImpl
import com.onopry.budgetapp.model.features.TransactionsModel

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private val categoryes = listOf(
        "Медицина", "Транспорт", "Продукты",
        "Развлечения", "Авто", "Дом",
        "Кафе", "Хобби", "Прочее")
    //private val moneyList = mutableListOf<Int>()
    private val transList = TransactionsModel(TransactionsDataSourseImpl()).getTransactions()
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        val addingMoneyFragment = AddingMoneyFragment()
        binding.transTextSample.text = "Транзакции asd"


        // RecyclerView инициализация всенр важного
        adapter = TransactionsAdapter()
        adapter.transactions = transList

        binding.transactionRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.transactionRecycler.adapter = adapter

        binding.fabTransaction.setOnClickListener {
            Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
            Log.d("LIasdasdasdST", "LIST = ${transList.size}")
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, addingMoneyFragment)
                .commit()
        }

        return binding.root
    }

    companion object {
        fun newInstance(): TransactionsFragment {
            val fragment = TransactionsFragment()
            return fragment
        }
    }
}