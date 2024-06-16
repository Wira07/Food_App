package com.wira_fkom.food_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.about.ProfileActivity
import com.wira_fkom.food_app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Halaman Utama"
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home action
                    true
                }

                R.id.navigation_favorite -> {
                    // Handle search action
                    true
                }

                R.id.navigation_profile -> {
                    // Handle profile action
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }
}
