package com.wira_fkom.food_app.about

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ActivityAboutBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set title and enable back button
        title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Fetch and display user profile data
        fetchUserProfile()

        // Set button click listener
        binding.botton.setOnClickListener {
            updateUserProfile()
        }
    }

    private fun fetchUserProfile() {
        database.child("users").child("userProfile").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userProfile = dataSnapshot.getValue(UserProfile::class.java)
                userProfile?.let {
                    binding.namaLengkap.editText?.setText(it.fullName)
                    binding.Email.editText?.setText(it.email)
                    binding.Linkedin.editText?.setText(it.linkedIn)
                    binding.Telepon.editText?.setText(it.Telepon)
                    binding.Github.editText?.setText(it.Github)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun updateUserProfile() {
        val fullName = binding.namaLengkap.editText?.text.toString()
        val email = binding.Email.editText?.text.toString()
        val linkedIn = binding.Linkedin.editText?.text.toString()
        val telepon = binding.Telepon.editText?.text.toString()
        val github = binding.Github.editText?.text.toString()

        val userProfile = UserProfile(fullName, email, linkedIn, telepon, github)

        // Write a message to the database
        database.child("users").child("userProfile").setValue(userProfile)
            .addOnSuccessListener {
                // Handle success, update UI
                binding.namaLengkap.editText?.setText(fullName)
                binding.Email.editText?.setText(email)
                binding.Linkedin.editText?.setText(linkedIn)
                binding.Telepon.editText?.setText(telepon)
                binding.Github.editText?.setText(github)
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Navigate back to HomeActivity
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
