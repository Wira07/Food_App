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
                R.id.navigation_home -> true
                R.id.navigation_favorite -> true
                R.id.navigation_profile -> {
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

        val names = resources.getStringArray(R.array.data_name)
        val descriptions = resources.getStringArray(R.array.data_description)

        val userProfiles = names.zip(descriptions).map {
            UserProfile(it.first, "", it.second)
        }

        val adapter = FoodViewAdapter(this, userProfiles)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }
}
