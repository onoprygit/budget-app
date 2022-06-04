package com.onopry.budgetapp.model.services

import android.util.Log
import com.onopry.budgetapp.utils.PeriodDate
import com.onopry.budgetapp.utils.PeriodRange
import java.time.LocalDate

typealias PeriodListener = (period: PeriodDate) -> Unit

class PeriodService{
    private var nowSelectedPeriod: PeriodDate
    private val listeners = mutableSetOf<PeriodListener>()

    init {
        val currentDate = LocalDate.now()
        val startDate: LocalDate = LocalDate.of(currentDate.year, currentDate.monthValue, 1)
        val finishDate = LocalDate.of(currentDate.year, currentDate.monthValue, currentDate.lengthOfMonth())
        nowSelectedPeriod = PeriodDate(startDate, finishDate, PeriodRange.MONTH)
        Log.d("initperiod", nowSelectedPeriod.toString())
    }

    fun setPeriod(startDate: LocalDate, finishDate: LocalDate, type: PeriodRange){
        nowSelectedPeriod =  PeriodDate(startDate, finishDate, type)
        notifyChanges()
    }

    fun setPeriod(date: LocalDate, type: PeriodRange){
        when(type){
            PeriodRange.MONTH -> {
                Log.d("ChooseButtonTAG", "service = $type")
                nowSelectedPeriod = getPeriodFromRange(date, type)
            }
            PeriodRange.YEAR -> {
                Log.d("ChooseButtonTAG", "service = $type")
                nowSelectedPeriod = getPeriodFromRange(date, type)
            }
            PeriodRange.WEEK -> {
                Log.d("ChooseButtonTAG", "service = $type")
                nowSelectedPeriod = getPeriodFromRange(date, type)
            }
            PeriodRange.OTHER -> {
                Log.d("ChooseButtonTAG", "service = $type")
                throw Exception("Задан произвольный период")
            }
        }
        notifyChanges()
    }

    private fun getPeriodFromRange(date: LocalDate, type: PeriodRange): PeriodDate {
        val startDate: LocalDate
        val finishDate: LocalDate
        when (type) {
            /*PeriodRange.WEEK -> {
                startDate = LocalDate.of(date.year, date.monthValue, 1)
                finishDate = LocalDate.of(date.year, date.monthValue, date.lengthOfMonth())
                return PeriodDate(startDate, finishDate, type)
            }*/
            PeriodRange.MONTH -> {
                startDate = LocalDate.of(date.year, date.monthValue, 1)
                finishDate = LocalDate.of(date.year, date.monthValue, date.lengthOfMonth())
            }
            PeriodRange.YEAR -> {
                startDate = LocalDate.of(date.year, 1, 1)
                finishDate = LocalDate.of(date.year, 12, 31)
                return PeriodDate(startDate, finishDate, type)
            }
            else -> {
                startDate = LocalDate.now()
                finishDate = LocalDate.now()
            }
        }
        return PeriodDate(startDate, finishDate, type)
    }

    fun addListener(listener: PeriodListener){
        listeners.add(listener)
        listener.invoke(nowSelectedPeriod)
    }

    fun removeListener(listener: PeriodListener){ listeners.remove(listener) }

    private fun notifyChanges(){ listeners.forEach { it.invoke(nowSelectedPeriod) } }


}