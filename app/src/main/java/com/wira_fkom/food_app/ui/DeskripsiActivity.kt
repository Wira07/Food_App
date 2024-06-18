package com.wira_fkom.food_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.databinding.ActivityDeskripsiBinding
class DeskripsiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeskripsiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeskripsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        binding.textViewDescription.text = description
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_favorite -> true
                R.id.navigation_profile -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }



}
