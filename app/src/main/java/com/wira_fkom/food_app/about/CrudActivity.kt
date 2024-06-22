package com.wira_fkom.food_app.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wira_fkom.food_app.databinding.ActivityCrudBinding
import com.wira_fkom.food_app.data.RequestBody
import com.wira_fkom.food_app.db.ApiResponse
import com.wira_fkom.food_app.db.ApiService
import com.wira_fkom.food_app.db.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrudActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val email = binding.etEmail.text.toString()
        val whatsapp = binding.etWhatsapp.text.toString()
        val instagram = binding.etInstagram.text.toString()
        val github = binding.etGithub.text.toString()

        val requestBody = RequestBody(
            action = "update",
            id = 1, // ID of the user to update
            email = email,
            whatsapp = whatsapp,
            instagram = instagram,
            github = github
        )

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.updateUserProfile(requestBody).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            Toast.makeText(this@CrudActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@CrudActivity, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.e("CrudActivity", "Error: ${it.message}")
                        }
                    }
                } else {
                    Log.e("CrudActivity", "Response unsuccessful")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("CrudActivity", "Failed to save profile")
            }
        })
    }
}
