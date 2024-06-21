package com.wira_fkom.food_app.db

import com.wira_fkom.food_app.data.Profile
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("profile_crud.php")
    fun readUserProfile(@Body requestBody: Profile): Call<ApiResponse>

    @POST("profile_crud.php")
    fun updateUserProfile(@Body requestBody: Profile): Call<ApiResponse>
}
















//import com.wira_fkom.food_app.data.Profile
//import okhttp3.RequestBody
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface ApiService {
//    @POST("profile_crud.php")
//    fun createUserProfile(@Body profile: Profile): Call<ApiResponse>
//
//    @POST("profile_crud.php")
//    fun readUserProfile(@Body profile: Map<String, Any>): Call<ApiResponse>
//
//    @POST("profile_crud.php")
//    fun updateUserProfile(@Body profile: Profile): Call<ApiResponse>
//
//    @POST("profile_crud.php")
//    fun deleteUserProfile(@Body requestBody: RequestBody): Call<ApiResponse>
//}