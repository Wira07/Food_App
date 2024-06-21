package com.wira_fkom.food_app.about

import com.wira_fkom.food_app.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.db.ApiResponse
import com.wira_fkom.food_app.db.ApiService
import com.wira_fkom.food_app.db.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var tvWhatsapp: TextView
    private lateinit var tvInstagram: TextView
    private lateinit var tvGithub: TextView
    private lateinit var btnEditProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvEmail = findViewById(R.id.tv_email)
        tvWhatsapp = findViewById(R.id.tv_whatsapp)
        tvInstagram = findViewById(R.id.tv_instagram)
        tvGithub = findViewById(R.id.tv_github)
        btnEditProfile = findViewById(R.id.btn_edit_profile)

        btnEditProfile.setOnClickListener {
            val intent = Intent(this, CrudActivity::class.java)
            startActivity(intent)
        }

        readProfile(1) // Retrieve profile for user with ID 1
    }

    private fun readProfile(id: Int) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val requestBody = Profile(action = "read", id = id)

        apiService.readUserProfile(requestBody).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            val profileData = it.data
                            profileData?.let { profile ->
                                tvEmail.text = profile.email
                                tvWhatsapp.text = profile.whatsapp
                                tvInstagram.text = profile.instagram
                                tvGithub.text = profile.github
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
