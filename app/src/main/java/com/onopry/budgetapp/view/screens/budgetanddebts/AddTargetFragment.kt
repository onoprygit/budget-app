package com.onopry.budgetapp.view.screens.budgetanddebts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onopry.budgetapp.databinding.FragmentAddTargetBinding
import com.onopry.budgetapp.model.dto.TargetDto
import com.onopry.budgetapp.view.screens.others.DatePickerFragment
import com.onopry.budgetapp.viewmodel.budgetanddebts.BudgetAndDebtsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class AddTargetFragment : BottomSheetDialogFragment(){

    private lateinit var target: TargetDto
    private lateinit var binding: FragmentAddTargetBinding

    private val viewModel: BudgetAndDebtsViewModel by viewModels()
    private lateinit var datePickerFragment: DatePickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        target = if (arguments != null) {
            val id = arguments!!.getString("targetID")
            viewModel.getTargetById(id!!)
        } else TargetDto.errorTarget()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTargetBinding.inflate(inflater, container, false)

        with(binding){
            addTargetName.setText(target.title)
            addTargetNeedMoney.setText(target.cost.toString())
            addTargetHasMoney.setText(target.currentAmount.toString())
            addTargetDatePick.text = "Сегодня"
        }

        binding.addTargetSaveBnt.setOnClickListener {
            setTargetData(target)
            viewModel.saveTarget(target)
            this.dismiss()
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
    private fun setTargetData(target: TargetDto?){
        val newTarget: TargetDto
        with(binding){
//            if (target == null) {
//                newTarget = TargetDTO(
//                    id = UUID.randomUUID().toString(),
//                    title = addTargetName.text.toString(),
//                    cost = addTargetNeedMoney.text.toString().toInt(),
//                    currentAmount = addTargetHasMoney.text.toString().toInt(),
//                    date = LocalDate.now().plusMonths(1)
//                )
//                this@AddTargetFragment.target = newTarget
//            } else {
            target!!.apply {
                title = addTargetName.text.toString()
                cost = addTargetNeedMoney.text.toString().toInt()
                currentAmount = addTargetHasMoney.text.toString().toInt()
                date = LocalDate.now().plusMonths(1)

            }
//            }
        }
    }

}