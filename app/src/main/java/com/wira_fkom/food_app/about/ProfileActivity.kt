package com.wira_fkom.food_app.about

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.data.ProfileDatabase
import com.wira_fkom.food_app.data.ProfileRepository
import com.wira_fkom.food_app.databinding.ActivityProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileDatabase: ProfileDatabase
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(ProfileRepository(profileDatabase.profileDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize profileDatabase
        profileDatabase = ProfileDatabase.getDatabase(this)

        setupButtons()

        // Observe all profiles
        profileViewModel.allProfiles.observe(this, Observer { profiles ->
            // Update UI with profiles data
        })
    }

    private fun setupButtons() {
        binding.btnCreate.setOnClickListener { createUser() }
        binding.btnRead.setOnClickListener { readUser() }
        binding.btnUpdate.setOnClickListener { updateUser() }
        binding.btnDelete.setOnClickListener { deleteUser() }
    }

    private fun createUser() {
        val user = collectUserData() ?: return

        profileViewModel.addProfile(user)
        Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun readUser() {
        val id = binding.etId.text.toString().toIntOrNull()
        if (id == null) {
            Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return
        }

        profileViewModel.getProfile(id).observe(this, Observer { user ->
            if (user != null) {
                populateUserData(user)
            } else {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser() {
        val id = binding.etId.text.toString().toIntOrNull()
        if (id == null) {
            Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return
        }

        val user = collectUserData() ?: return

        profileViewModel.updateProfile(user.copy(id = id))
        Toast.makeText(this, "User updated successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser() {
        val id = binding.etId.text.toString().toIntOrNull()
        if (id == null) {
            Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                profileDatabase.profileDao().getProfile(id)
            }
            withContext(Dispatchers.Main) {
                if (user != null) {
                    withContext(Dispatchers.IO) {
                        profileViewModel.deleteProfile(user)
                    }
                    Toast.makeText(this@ProfileActivity, "User deleted successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProfileActivity, "User not found!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun collectUserData(): Profile? {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return null
        }

        return Profile(name = name, email = email, phone = phone)
    }

    private fun populateUserData(user: Profile) {
        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etPhone.setText(user.phone)
        binding.etId.setText(user.id.toString())  // Populate the ID field
    }
}
