package com.wira_fkom.food_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.about.ProfileActivity
import com.wira_fkom.food_app.adapter.FoodViewAdapter
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ActivityHomeBinding
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Halaman Utama"
        setupBottomNavigation()
        setupRecyclerView()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already in home, do nothing
                    true
                }

                R.id.navigation_favorite -> {
                    // Handle favorite action (example)
                    // Replace with your logic
                    true
                }

                R.id.navigation_profile -> {
                    // Navigate to profile activity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewRecipes
        // Dummy data for RecyclerView (replace with your actual data)
        val dummyProfiles = listOf(
            UserProfile("Ahmad Dahlan", "", "", "", ""),
            UserProfile("Ahmad Yani", "", "", "", ""),
            UserProfile("Sutomo", "", "", "", ""),
            UserProfile("Sutomo", "", "", "", ""),
            UserProfile("Sutomo", "", "", "", ""),
            UserProfile("Sutomo", "", "", "", ""),
            UserProfile("Sutomo", "", "", "", ""),
            UserProfile("Sutomo", "", "", "", ""),
            // Add more profiles as needed
        )

        val adapter = FoodViewAdapter(this, dummyProfiles)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)  // Optionally, if size of RecyclerView doesn't change
    }
}
