package com.onopry.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.ActivityMainBinding.inflate
import com.onopry.budgetapp.databinding.FragmentAddingMoneyBinding
import com.onopry.budgetapp.databinding.FragmentMoreBinding

class AddingMoneyFragment : Fragment() {

    private lateinit var binding: FragmentAddingMoneyBinding
    private val categoryes = mutableListOf(
        "Медицина", "Транспорт", "Продукты",
        "Развлечения", "Авто", "Дом",
        "Кафе", "Хобби", "Прочее")
    private val moneyList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddingMoneyBinding.inflate(inflater, container, false)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            moneyList)
        binding.listMoney.adapter = adapter

        binding.addButton.setOnClickListener {
            moneyList.add(
                binding.editText.text.toString().toInt()
            )
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }
}