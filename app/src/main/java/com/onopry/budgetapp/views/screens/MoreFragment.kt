package com.onopry.budgetapp.views.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.onopry.budgetapp.databinding.FragmentMoreBinding
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.viewmodels.MoreViewModel


class MoreFragment : Fragment() {

    private val viewModel: MoreViewModel by viewModels { startFactory() }
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        binding.moreTextSample.text = "Фрагмент Еще"
        return binding.root
    }

    companion object {
        fun newInstance(): MoreFragment {
            val fragment = MoreFragment()
            return fragment
        }
    }
}