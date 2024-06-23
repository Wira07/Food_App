package com.wira_fkom.food_app.data

data class RequestBody(
    val action: String,
    val id: Int? = null,
    val name: String,
    val email: String,
    val phone: String
)
