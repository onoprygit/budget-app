package com.onopry.budgetapp.model.dto

data class UserDto(
    val uid: String,
    val mail: String,
    val name: String = "Name",
    val surname: String = "Surname"
)
