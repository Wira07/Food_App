package com.wira_fkom.food_app.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.wira_fkom.food_app.databinding.ActivityForgotPasswordBinding
import com.wira_fkom.food_app.db.DbContract
import java.nio.charset.Charset

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Forgot Password"

        binding.btnResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString()

            if (email.isNotEmpty()) {
                val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val url = DbContract.urlForgotPassword

                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        if (response == "Email Sent") {
                            Toast.makeText(applicationContext, "Reset Password Email Sent", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Failed to Send Email", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["email"] = email
                        return params
                    }

                    override fun getBodyContentType(): String {
                        return "application/x-www-form-urlencoded; charset=UTF-8"
                    }
                }

                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(applicationContext, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
