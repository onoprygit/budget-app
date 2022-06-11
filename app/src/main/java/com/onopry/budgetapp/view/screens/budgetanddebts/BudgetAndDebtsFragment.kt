package com.onopry.budgetapp.view.screens.budgetanddebts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.onopry.budgetapp.adapters.BudgetsAdapter
import com.onopry.budgetapp.databinding.FragmentBudetAndDebtsBinding
import com.onopry.budgetapp.viewmodel.budgetanddebts.BudgetAndDebtsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonDisposableHandle.parent

@AndroidEntryPoint
class BudgetAndDebtsFragment : Fragment() {

    private val viewModel: BudgetAndDebtsViewModel by viewModels()
    private lateinit var binding: FragmentBudetAndDebtsBinding
    private val targetsAdapter = BudgetsAdapter()
    private lateinit var bottomSheet: AddTargetFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudetAndDebtsBinding.inflate(inflater, container, false)

        viewModel.tars.observe(viewLifecycleOwner){
            targetsAdapter.targetList = it
        }

        binding.targetRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.targetRecycler.adapter = targetsAdapter

        binding.budgetIcAdd.setOnClickListener {
            AddTargetFragment().show(parentFragmentManager, null)
        }

        return binding.root
    }

    companion object {
        fun newInstance(): BudgetAndDebtsFragment {
            val fragment = BudgetAndDebtsFragment()
            return fragment
        }
    }
}