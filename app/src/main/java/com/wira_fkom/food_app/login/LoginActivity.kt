package com.wira_fkom.food_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.wira_fkom.food_app.databinding.ActivityLoginBinding
import com.wira_fkom.food_app.db.DbContract
import com.wira_fkom.food_app.ui.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Login"

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)

                val stringRequest = StringRequest(
                    Request.Method.GET, "${DbContract.urlLogin}?username=$email&password=$password",
                    { response ->
                        if (response == "Selamat Datang") {
                            Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Login Gagal", Toast.LENGTH_SHORT).show()
                        }
                    },
                    { error ->
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                    })

                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(applicationContext, "Password Atau Username Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//class LoginActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityLoginBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        title = "Login"
//
//        binding.btnRegister.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}