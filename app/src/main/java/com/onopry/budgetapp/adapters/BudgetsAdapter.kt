package com.onopry.budgetapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onopry.budgetapp.databinding.ItemTargetBinding
import com.onopry.budgetapp.model.dto.TargetDto

typealias TargetsActionListener = (target: TargetDto) -> Unit

class BudgetDuffCallback(
    private val oldList: List<TargetDto>,
    private val newList: List<TargetDto>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldPos: Int, newPos: Int) =
        oldList[oldPos] == newList[newPos]
}

class BudgetsAdapter(

): RecyclerView.Adapter<BudgetsAdapter.BudgetsViewHolder>(), View.OnClickListener {

    var targetList: List<TargetDto> = emptyList()
        set(newValue) {
            val diffCAll = BudgetDuffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCAll)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTargetBinding.inflate(inflater, parent, false)
        return BudgetsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BudgetsViewHolder, position: Int) {
        val target = targetList[position]
        with(holder.binding) {
            targetTitle.text = target.title
            targetFinishDate.text = "До " + target.date.toString()
            targetCost.text = target.cost.toString()
            targetCurrentSum.text = target.currentAmount.toString()
            targetDaysRest.text = "Осталось дней: " + target.restDay

            targetProgressBar.progress = target.restMoney
        }
    }

    override fun getItemCount() = targetList.size

    class BudgetsViewHolder(
        val binding: ItemTargetBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(p0: View?) {

    }

}