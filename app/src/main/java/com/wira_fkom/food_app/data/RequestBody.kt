package com.wira_fkom.food_app.data

data class RequestBody(
    val action: String,
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null
)
