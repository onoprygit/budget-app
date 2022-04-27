package com.onopry.budgetapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.onopry.budgetapp.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {

    private lateinit var binding : FragmentAnalyticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false)

        binding.textHome.text = "Фрагмент \"Аналитика\""
        return binding.root
    }
}