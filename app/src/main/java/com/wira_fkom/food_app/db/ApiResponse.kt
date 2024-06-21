package com.wira_fkom.food_app.db

import com.wira_fkom.food_app.data.Profile

data class ApiResponse(
    val status: String,
    val data: Profile?,
    val message: String?
)
