package com.wira_fkom.food_app.db

import com.wira_fkom.food_app.data.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST
    fun readUserProfile(@Url url: String, @Body requestBody: RequestBody): Call<ApiResponse>

    @POST
    fun updateUserProfile(@Url url: String, @Body requestBody: RequestBody): Call<ApiResponse>
}
