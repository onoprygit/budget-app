package com.onopry.budgetapp.model

import com.onopry.budgetapp.model.dto.OperationsDto
import com.onopry.budgetapp.model.dto.TargetDTO
import com.onopry.budgetapp.utils.TargetNotFoundException
import java.time.LocalDate

typealias TargetListener = (target: List<TargetDTO>) -> Unit

class TargetService {
    private var targetList = mutableListOf<TargetDTO>()
    private val listeners = mutableSetOf<TargetListener>()

    init {
        loadTargets()
    }

    private fun loadTargets(){
        targetList = mutableListOf(
            TargetDTO(
                id = "720cb4c2-46e6-48e6-8098-87625b866802",
                title = "На машину",
                cost = 700000,
                currentAmount = 0,
                date = LocalDate.of(2022, 6, 20)
            ),
            TargetDTO(
                id = "f5ced627-5fab-41ef-88d8-19599fae79ef",
                title = "На гараж",
                cost = 80000,
                currentAmount = 20000,
                date = LocalDate.of(2022, 7, 12)
            ))
    }

    fun getTargetById(id: String): TargetDTO{
        val index = targetList.indexOfFirst { it.id == id }
        if (index == -1 )
            throw TargetNotFoundException()
        return targetList[index]
    }

    fun addTarget(target: TargetDTO){
        if (!isTargetExist(target)) {
            targetList.add(target)
        }
        notifyChanges()
    }

    fun editTarget(target: TargetDTO){
        val index = targetList.indexOfFirst { it.id == target.id }
        if (index != -1){
            with(targetList[index]){
                title = target.title
                cost = target.cost
                currentAmount = target.currentAmount
                date = target.date
                description = target.description
            }
        }
        notifyChanges()
    }

    fun isTargetExist(target: TargetDTO) =
        targetList.indexOfFirst { target.id == it.id } != -1

    /*  при "синхронизации" операции с целью
        проверяет не стала ли она выполнена
        и если так, то меняем флаг, чтобы цель
        не отображалась
    */
    fun addOperationToTarget(operation: OperationsDto){
        val id = operation.category.targetId
        if (id != null){
            val target = getTargetById(id)
            target.currentAmount += operation.amount

            if (hasTargetDone(target))
                setDoneTarget(target)
        }
    }

    private fun hasTargetDone(target: TargetDTO) = target.cost <= target.currentAmount


    /*fun isTargetDone(id: String): Boolean {
        val target = getTargetById(id)
        return target.cost <= target.currentAmount
    }*/

    private fun setDoneTarget(target: TargetDTO){
        target.isDone = true
        removeTarget(target)
    }

    private fun removeTarget(target: TargetDTO){
        targetList.remove(target)
        notifyChanges()
    }

    fun addListener(listener: TargetListener){
        listeners.add(listener)
        listener.invoke(targetList)
    }

    fun removeListener(listener: TargetListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(targetList) } }


}