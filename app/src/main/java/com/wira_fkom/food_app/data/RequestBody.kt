package com.wira_fkom.food_app.data

data class RequestBody(
    val action: String,
    val id: Int? = null,
    val email: String? = null,
    val whatsapp: String? = null,
    val instagram: String? = null,
    val github: String? = null
)
