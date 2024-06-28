package com.wira_fkom.food_app.about

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.data.ProfileDatabase
import com.wira_fkom.food_app.databinding.ActivityProfileDetailsBinding
import com.wira_fkom.food_app.ui.FavoriteActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailsBinding
    private lateinit var profileDatabase: ProfileDatabase
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Profile Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize profileDatabase
        profileDatabase = ProfileDatabase.getDatabase(this)

        profile = intent.getParcelableExtra<Profile>("profile")
        profile?.let { populateUserData(it) }

        binding.btnSave.setOnClickListener { saveUserData() }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java).apply {
                        // Assuming you have an adapter object and getFavorites() method in scope
                        // putParcelableArrayListExtra("EXTRA_FAVORITES", ArrayList(adapter.getFavorites()))
                    }
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileDetailsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun populateUserData(user: Profile) {
        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etPhone.setText(user.phone)
        binding.tvId.text = user.id.toString()
    }

    private fun saveUserData() {
        profile?.let {
            val updatedProfile = it.copy(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                phone = binding.etPhone.text.toString()
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    profileDatabase.profileDao().updateProfile(updatedProfile)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfileDetailsActivity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
