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
            override fun onTransactionAdd(transaction: TransactionsDto) {
                Toast.makeText(requireContext(), "ADD", Toast.LENGTH_SHORT).show()
            }
            override fun onTransactionEdit(transaction: TransactionsDto) {
                Toast.makeText(requireContext(), "EDIT", Toast.LENGTH_SHORT).show()
                startEditFragment(AddingMoneyFragment.newInstance("transactionTag",transaction))
            }
        })


        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            adapter.transactionList = it
        })

        binding.transactionRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.transactionRecycler.adapter = adapter

        binding.fabTransaction.setOnClickListener {
            startEditFragment(AddingMoneyFragment())
        }

        return binding.root
    }

    companion object {
        fun newInstance(): TransactionsFragment {
            val fragment = TransactionsFragment()
            return fragment
        }
    }

    fun startEditFragment(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    }
