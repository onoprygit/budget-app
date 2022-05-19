package com.onopry.budgetapp.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onopry.budgetapp.App
import com.onopry.budgetapp.adapters.TransactionsAdapter
import com.onopry.budgetapp.R
import com.onopry.budgetapp.adapters.TransactionActionListener
import com.onopry.budgetapp.databinding.FragmentTransactionsBinding
import com.onopry.budgetapp.model.TransactionService
import com.onopry.budgetapp.model.TransactionsListener
import com.onopry.budgetapp.model.dto.TransactionsDto
import com.onopry.budgetapp.model.features.TransactionsDataSourseImpl
import com.onopry.budgetapp.model.features.TransactionsModel
import com.onopry.budgetapp.viewmodels.TransactionsViewModel
import com.onopry.budgetapp.viewmodels.startFactory

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    //private val transList = TransactionsModel(TransactionsDataSourseImpl()).getTransactions()
    private lateinit var adapter: TransactionsAdapter

    // TODO: Разобраться с делегатами
    private val viewModel: TransactionsViewModel by viewModels { startFactory() }

//    private val transactionService: TransactionService
//        get() = ( as App).transactionsService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        // RecyclerView инициализация всенр важного

        //transactionService.addListener(transactionsListener)


        adapter = TransactionsAdapter(object : TransactionActionListener {
            override fun onTransactionDelete(transaction: TransactionsDto) {
                viewModel.deleteTransaction(transaction)
            }

            override fun onTransactionAdd(transaction: TransactionsDto) {
//                viewModel.addTransaction(transaction)
                Toast.makeText(requireContext(), "ADD", Toast.LENGTH_SHORT).show()
            }

            override fun onTransactionEdit(transaction: TransactionsDto) {
                Toast.makeText(requireContext(), "EDIT", Toast.LENGTH_SHORT).show()
            }
        })


        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            adapter.transactionList = it
        })

        binding.transactionRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.transactionRecycler.adapter = adapter

        binding.fabTransaction.setOnClickListener {
            val addingMoneyFragment = AddingMoneyFragment()
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, addingMoneyFragment)
                .commit()
        }

        return binding.root
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        transactionService.removeListener(transactionsListener)
//    }

//    private val transactionsListener: TransactionsListener = {
//        adapter.transactionList = it
//    }

    companion object {
        fun newInstance(): TransactionsFragment {
            val fragment = TransactionsFragment()
            return fragment
        }
    }
}