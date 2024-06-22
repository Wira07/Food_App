package com.wira_fkom.food_app.about

import android.content.Intent
import android.os.Bundle
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

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, CrudActivity::class.java)
            startActivity(intent)
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
                                binding.tvEmail.text = profile.email
                                binding.tvWhatsapp.text = profile.whatsapp
                                binding.tvInstagram.text = profile.instagram
                                binding.tvGithub.text = profile.github
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
}
