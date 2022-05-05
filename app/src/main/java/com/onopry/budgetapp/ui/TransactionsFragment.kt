package com.onopry.budgetapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.onopry.budgetapp.R
import com.onopry.budgetapp.dataStarage.TransactionDataStorage
import com.onopry.budgetapp.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private val categoryes = listOf(
        "Медицина", "Транспорт", "Продукты",
        "Развлечения", "Авто", "Дом",
        "Кафе", "Хобби", "Прочее")
    private val moneyList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        val addingMoneyFragment = AddingMoneyFragment()
//        val blankFragment = BlankFragment()
        binding.transTextSample.text = "Транзакции"
//        val bs = binding.root.findViewById<View>(R.layout.fragment_blank)
//        BottomSheetBehavior.from(bs)





        binding.fabTransaction.setOnClickListener {
            Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
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