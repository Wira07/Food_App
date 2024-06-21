package com.wira_fkom.food_app.about

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.db.ApiResponse
import com.wira_fkom.food_app.db.ApiService
import com.wira_fkom.food_app.db.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrudActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etWhatsapp: EditText
    private lateinit var etInstagram: EditText
    private lateinit var etGithub: EditText
    private lateinit var btnSaveProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        etEmail = findViewById(R.id.et_email)
        etWhatsapp = findViewById(R.id.et_whatsapp)
        etInstagram = findViewById(R.id.et_instagram)
        etGithub = findViewById(R.id.et_github)
        btnSaveProfile = findViewById(R.id.btn_save_profile)

        btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val email = etEmail.text.toString()
        val whatsapp = etWhatsapp.text.toString()
        val instagram = etInstagram.text.toString()
        val github = etGithub.text.toString()

        val requestBody = Profile(
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
                            val intent = Intent(this@CrudActivity, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
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
