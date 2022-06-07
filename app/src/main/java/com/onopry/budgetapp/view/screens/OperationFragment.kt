package com.onopry.budgetapp.view.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.onopry.budgetapp.adapters.OperationsAdapter
import com.onopry.budgetapp.R
import com.onopry.budgetapp.adapters.OperationActionListener
import com.onopry.budgetapp.databinding.FragmentOperationBinding
import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.utils.navigator
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodel.operations.OperationsViewModel

class OperationFragment : Fragment() {

    private lateinit var binding: FragmentOperationBinding
    private lateinit var adapter: OperationsAdapter

    private val viewModel: OperationsViewModel by viewModels { startFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOperationBinding.inflate(inflater, container, false)

        adapter = OperationsAdapter(viewModel.getService() ,object : OperationActionListener {
            override fun onOperationDelete(operation: OperationsDto) {
                viewModel.deleteOperation(operation)
            }

            override fun onOperationEdit(operation: OperationsDto) {
                navigator().showEditOperationScreen(operation.id)
            }
        })

        viewModel.operations.observe(viewLifecycleOwner) { adapter.operationList = it }

        binding.transactionRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.transactionRecycler.adapter = adapter

        binding.fabTransaction.setOnClickListener {
            navigator().showAddOperationScreen()
        }

        return binding.root
    }

    companion object {
        fun newInstance() = OperationFragment()
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

    val TAG = "FRAGMENTDESTROY_TAG"
    
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}
