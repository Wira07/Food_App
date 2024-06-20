package com.wira_fkom.food_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.about.ProfileActivity
import com.wira_fkom.food_app.adapter.DescriptionAdapter
import com.wira_fkom.food_app.databinding.ActivityDeskripsiBinding

class DeskripsiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeskripsiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeskripsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Deskripsi Resep Makanan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupBottomNavigation()

        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        if (description != null) {
            val descriptionList = description.split("\n")
            binding.recyclerViewDescription.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewDescription.adapter = DescriptionAdapter(descriptionList)
        }

        val imageResId = intent.getIntExtra("EXTRA_IMAGE_RES_ID", R.drawable.potato) // default image
        binding.image.setImageResource(imageResId)
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
                R.id.navigation_favorite -> {
                    // Handle favorite navigation
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // Navigate back to the previous activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
