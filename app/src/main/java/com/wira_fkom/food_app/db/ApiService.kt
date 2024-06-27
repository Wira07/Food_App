package com.wira_fkom.food_app.db

import com.wira_fkom.food_app.data.Profile
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("users/create")
    fun createUser(@Body user: Profile): Call<Void>

    @GET("users/{id}")
    fun readUser(@Path("id") id: Int): Call<Profile>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: Profile): Call<Void>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}