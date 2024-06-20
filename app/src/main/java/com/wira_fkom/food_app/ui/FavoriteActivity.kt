package com.wira_fkom.food_app.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wira_fkom.food_app.adapter.FoodViewAdapter
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FoodViewAdapter
    private lateinit var favoriteList: List<UserProfile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Resep Favorit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteList = intent.getParcelableArrayListExtra("EXTRA_FAVORITES") ?: listOf()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = FoodViewAdapter(this, favoriteList)
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavorites.adapter = adapter
        binding.recyclerViewFavorites.setHasFixedSize(true)
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
