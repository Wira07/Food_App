package com.wira_fkom.food_app.db

import com.wira_fkom.food_app.data.UserProfile

data class ApiResponse(
    val status: String,
    val message: String,
    val data: UserProfile? = null
)