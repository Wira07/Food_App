package com.wira_fkom.food_app.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wira_fkom.food_app.data.Profile
import com.wira_fkom.food_app.databinding.ActivityProfileDetailsBinding

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profile = intent.getParcelableExtra<Profile>("profile")
        profile?.let { populateUserData(it) }
    }

    private fun populateUserData(user: Profile) {
        binding.tvName.text = user.name
        binding.tvEmail.text = user.email
        binding.tvPhone.text = user.phone
        binding.tvId.text = user.id.toString()
    }
}
