package com.wira_fkom.food_app.about

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.data.ProfileDatabase
import com.wira_fkom.food_app.databinding.ActivityProfileDetailsBinding
import com.wira_fkom.food_app.ui.FavoriteActivity
import com.wira_fkom.food_app.ui.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.IOException

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailsBinding
    private lateinit var profileDatabase: ProfileDatabase
    private var profile: Profile? = null

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openGallery()
        } else {
            Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let {
                val imagePath = saveImageToInternalStorage(it)
                binding.imgProfile.setImageURI(Uri.parse(imagePath))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Profile Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profileDatabase = ProfileDatabase.getDatabase(this)

        profile = intent.getParcelableExtra("profile")
        profile?.let { populateUserData(it) }

        binding.btnSave.setOnClickListener { saveUserData() }
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.imgProfile.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openGallery()
            }
        }

        setupBottomNavigation()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.navigation_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java).apply {
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

    private fun saveImageToInternalStorage(imageUri: Uri): String? {
        val fileName = "profile_image_${System.currentTimeMillis()}.jpg"
        var fos: FileOutputStream? = null
        return try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            fos = openFileOutput(fileName, MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            "$filesDir/$fileName"
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            fos?.close()
        }
    }
}