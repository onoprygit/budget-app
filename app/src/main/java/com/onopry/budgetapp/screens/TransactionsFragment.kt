package com.onopry.budgetapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.onopry.budgetapp.adapters.TransactionsAdapter
import com.onopry.budgetapp.R
import com.onopry.budgetapp.adapters.TransactionActionListener
import com.onopry.budgetapp.databinding.FragmentTransactionsBinding
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.TransactionsViewModel



class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var adapter: TransactionsAdapter

    // TODO: Разобраться с делегатами
    private val viewModel: TransactionsViewModel by viewModels { startFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        adapter = TransactionsAdapter(object : TransactionActionListener {
            override fun onTransactionDelete(transaction: TransactionsDto) {
                viewModel.deleteTransaction(transaction)
            }

            override fun onTransactionEdit(transaction: TransactionsDto) {
                navigator().showEditOperationScreen(transaction.id)
            }
        })

        viewModel.transactions.observe(viewLifecycleOwner) { adapter.transactionList = it }

        binding.transactionRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.transactionRecycler.adapter = adapter

        binding.fabTransaction.setOnClickListener {
            navigator().showAddOperationScreen()
        }

        return binding.root
    }

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    fun startFragmentToEdit(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun startAddingToAdd(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
