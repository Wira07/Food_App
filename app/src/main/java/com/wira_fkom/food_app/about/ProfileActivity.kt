package com.wira_fkom.food_app.about

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ActivityAboutBinding
import com.wira_fkom.food_app.ui.HomeActivity
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityAboutBinding
    private lateinit var auth: FirebaseAuth
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        // Set title and enable back button
        title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Fetch and display user profile data
        fetchUserProfile()

        setupBottomNavigation()

        // Set button click listener
        binding.button.setOnClickListener {
            updateUserProfile()
        }

        // Set profile image click listener
        binding.profileImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
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
                    binding.Telepon.editText?.setText(it.telepon)
                    binding.Github.editText?.setText(it.github)
                    if (it.profileImage.isNotEmpty()) {
                        // Load the profile image using Glide or any other image loading library
                        Glide.with(this@ProfileActivity).load(it.profileImage).into(binding.profileImage)
                    }
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

        var userProfile = UserProfile(fullName, email, linkedIn, telepon, github, "")

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

        // Upload profile image if selected
        filePath?.let {
            val ref = storageReference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(it)
                .addOnSuccessListener { taskSnapshot ->
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        userProfile = UserProfile(fullName, email, linkedIn, telepon, github, uri.toString())
                        database.child("users").child("userProfile").setValue(userProfile)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.profileImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_favorite -> true
                R.id.navigation_profile -> true
                else -> false
            }
        }
    }
}
