package com.wira_fkom.food_app.db

import com.wira_fkom.food_app.data.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("save_profile.php") // Adjust the endpoint if necessary
    fun updateUserProfile(@Body requestBody: RequestBody): Call<ApiResponse>

    @POST("read_profile.php") // Adjust the endpoint if necessary
    fun readUserProfile(@Body requestBody: RequestBody): Call<ApiResponse>
}
