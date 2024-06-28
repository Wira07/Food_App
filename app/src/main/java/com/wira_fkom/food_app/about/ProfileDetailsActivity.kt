package com.wira_fkom.food_app.about

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.data.ProfileDatabase
import com.wira_fkom.food_app.databinding.ActivityProfileDetailsBinding
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
    }

    private fun populateUserData(user: Profile) {
        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etPhone.setText(user.phone)
        binding.tvId.text = user.id.toString()
    }

    private fun saveUserData() {
        val updatedProfile = profile?.copy(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString()
        ) ?: return

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
