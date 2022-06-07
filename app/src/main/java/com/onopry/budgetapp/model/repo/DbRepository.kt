package com.onopry.budgetapp.model.repo

interface DbRepository {

    fun load()

    fun update()

    fun loadSingle()

    fun updateSingle()
}