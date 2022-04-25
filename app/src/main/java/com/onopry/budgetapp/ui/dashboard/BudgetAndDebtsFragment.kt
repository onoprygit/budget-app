package com.onopry.budgetapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.databinding.FragmentBudetAndDebtsBinding

class BudgetAndDebtsFragment : Fragment() {

    private lateinit var binding: FragmentBudetAndDebtsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudetAndDebtsBinding.inflate(inflater, container, false)
        binding.textBudgetAndDebts.text = """
                Фрагмент:
                Бюджет и долги
            """.trimIndent()




        return binding.root
    }
    /*    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(BudgetAndDebtsViewModel::class.java)

        binding = FragmentBudetAndDebtsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }*/
}