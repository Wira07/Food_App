package com.wira_fkom.food_app.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.about.ProfileActivity
import com.wira_fkom.food_app.adapter.FoodViewAdapter
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: FoodViewAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var userProfiles: List<UserProfile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Halaman Utama"
        setupBottomNavigation()
        setupRecyclerView()
        setupSearch()

        binding.progressBar.visibility = View.GONE
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java).apply {
                        putParcelableArrayListExtra("EXTRA_FAVORITES", ArrayList(adapter.getFavorites()))
                    }
                    startActivity(intent)
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

    private fun setupRecyclerView() {
        binding.progressBar.visibility = View.VISIBLE

        val recyclerView = binding.recyclerViewRecipes

        val names = resources.getStringArray(R.array.data_name)
        val descriptionsArray = resources.getStringArray(R.array.data_description)
        val imageResIds = resources.obtainTypedArray(R.array.data_photo)

        userProfiles = names.mapIndexed { index, name ->
            UserProfile(
                name,
                imageResIds.getResourceId(index, -1),
                descriptionsArray[index].split("\n")
            )
        }

        imageResIds.recycle()

        adapter = FoodViewAdapter(this, userProfiles)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.progressBar.visibility = View.GONE
    }

    private fun setupSearch() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().lowercase()
                filterProfiles(searchText)
            }
        })
    }

    private fun filterProfiles(query: String) {
        val filteredProfiles = userProfiles.filter {
            it.name.lowercase().contains(query)
        }
        adapter.updateData(filteredProfiles)
    }
}