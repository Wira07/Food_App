package com.wira_fkom.food_app.about

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wira_fkom.food_app.databinding.ActivityProfileBinding
import com.wira_fkom.food_app.data.RequestBody
import com.wira_fkom.food_app.db.ApiResponse
import com.wira_fkom.food_app.db.ApiService
import com.wira_fkom.food_app.db.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            saveProfile()
        }

        readProfile(1) // Retrieve profile for user with ID 1
    }

    private fun readProfile(id: Int) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val requestBody = RequestBody(action = "read", id = id)

        apiService.readUserProfile(requestBody).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            val profileData = it.data
                            profileData?.let { profile ->
                                binding.etName.setText(profile.name)
                                binding.etEmail.setText(profile.email)
                                binding.etPhone.setText(profile.phone)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun saveProfile() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        val requestBody = RequestBody(
            action = "update",
            id = 1, // ID of the user to update
            name = name,
            email = email,
            phone = phone
        )

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.updateUserProfile(requestBody).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            Toast.makeText(this@ProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("ProfileActivity", "Error: ${it.message}")
                        }
                    }
                } else {
                    Log.e("ProfileActivity", "Response unsuccessful")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("ProfileActivity", "Failed to save profile")
            }
        })
    }
}
