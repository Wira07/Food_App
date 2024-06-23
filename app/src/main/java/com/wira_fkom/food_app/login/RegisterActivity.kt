package com.wira_fkom.food_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.wira_fkom.food_app.databinding.ActivityRegisterBinding
import com.wira_fkom.food_app.db.DbContract

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val conPassword = binding.etConPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && conPassword.isNotEmpty()) {
                if (password == conPassword) {
                    val stringRequest = object : StringRequest(
                        Request.Method.POST, DbContract.urlRegister,
                        { response ->
                            Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                        },
                        { error ->
                            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["username"] = email
                            params["password"] = password
                            return params
                        }
                    }

                    val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
                    requestQueue.add(stringRequest)
                } else {
                    Toast.makeText(applicationContext, "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
