package com.wira_fkom.food_app.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.about.ProfileActivity
import com.wira_fkom.food_app.adapter.DescriptionAdapter
import com.wira_fkom.food_app.adapter.FoodViewAdapter
import com.wira_fkom.food_app.databinding.ActivityDeskripsiBinding

class DeskripsiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeskripsiBinding
    private lateinit var adapter: FoodViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeskripsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Deskripsi Resep Makanan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupBottomNavigation()

        val descriptions = intent.getStringArrayListExtra("EXTRA_DESCRIPTION")
        if (descriptions != null) {
            binding.recyclerViewDescription.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewDescription.adapter = DescriptionAdapter(descriptions)
        }

        val imageResId = intent.getIntExtra("EXTRA_IMAGE_RES_ID", R.drawable.potato)
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
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
//                    val intent = Intent(this, FavoriteActivity::class.java).apply {
//                        putParcelableArrayListExtra("EXTRA_FAVORITES", ArrayList(adapter.getFavorites()))
//                    }
//                    startActivity(intent)
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
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
