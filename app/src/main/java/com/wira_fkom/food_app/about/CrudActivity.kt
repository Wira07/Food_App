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
import com.wira_fkom.food_app.db.DbContract
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
            if (validateInput()) {
                saveProfile()
            }
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = "Nama harus diisi"
            binding.etName.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email harus diisi"
            binding.etEmail.requestFocus()
            return false
        }

        if (phone.isEmpty()) {
            binding.etPhone.error = "Telepon harus diisi"
            binding.etPhone.requestFocus()
            return false
        }

        return true
    }

    private fun saveProfile() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        val requestBody = RequestBody(
            action = "update",
            id = 1, // ID of the user to update
            name = name,
            email = email,
            phone = phone
        )

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.updateUserProfile(DbContract.update_user_profile, requestBody).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            Toast.makeText(this@CrudActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent()
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            Log.e("CrudActivity", "Error: ${it.message}")
                            Toast.makeText(this@CrudActivity, "Failed to update profile: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("CrudActivity", "Response unsuccessful")
                    Toast.makeText(this@CrudActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("CrudActivity", "Failed to save profile")
                Toast.makeText(this@CrudActivity, "Failed to save profile", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
