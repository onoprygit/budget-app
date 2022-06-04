package com.onopry.budgetapp.view.screens.targets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onopry.budgetapp.databinding.FragmentAddTargetBinding
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.view.screens.DatePickerFragment
import com.onopry.budgetapp.viewmodel.screens.targets.AddTargetViewModel
import java.time.LocalDate
import java.util.*

class AddTargetFragment : BottomSheetDialogFragment(){
//    DialogFragment() {

    private var target: TargetDTO? = null
    private lateinit var binding: FragmentAddTargetBinding
    private val viewModel: AddTargetViewModel by viewModels { startFactory() }
    private lateinit var datePickerFragment: DatePickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            target = it.getString("targetID")
//        }
        if (arguments != null) {
            val id: String? = arguments?.getString("targetID")
            target = viewModel.getTargetById(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTargetBinding.inflate(inflater, container, false)

        with(binding){
            addTargetName.setText(target?.title)
            addTargetNeedMoney.setText(target?.cost?.toString())
            addTargetHasMoney.setText(target?.currentAmount?.toString())
            addTargetDatePick.text = target?.date.toString()
        }

        binding.addTargetSaveBnt.setOnClickListener {
            setTargetData(target)
            viewModel.saveTarget(target!!)
        }

        return binding.root
    }
    companion object {
        fun newInstance(key: String, value: String) =
            AddTargetFragment().apply {
                arguments = Bundle().apply {
                    putString(key, value)
                }
            }

        fun newInstance() = AddTargetFragment()
    }

    //todo сделать нормально, с val полями.
    private fun setTargetData(target_: TargetDTO?){
//        val target = TargetDTO()
        val newTarget: TargetDTO
        with(binding){
            if (target_ == null) {
                newTarget = TargetDTO(
                    id = UUID.randomUUID().toString(),
                    title = addTargetName.text.toString(),
                    cost = addTargetNeedMoney.text.toString().toInt(),
                    currentAmount = addTargetHasMoney.text.toString().toInt(),
                    date = LocalDate.now().plusMonths(1)
                )
                target = newTarget
            } else {
                target_.apply {
                    title = addTargetName.text.toString()
                    cost = addTargetNeedMoney.text.toString().toInt()
                    currentAmount = addTargetHasMoney.text.toString().toInt()
                    date = LocalDate.now().plusMonths(1)
                }
            }
        }
    }

}