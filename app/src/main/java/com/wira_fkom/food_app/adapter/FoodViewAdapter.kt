package com.wira_fkom.food_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ItemRecipeBinding
import com.wira_fkom.food_app.ui.DeskripsiActivity

class FoodViewAdapter(
    private val context: Context,
    private val userProfiles: List<UserProfile>
) : RecyclerView.Adapter<FoodViewAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val userProfile = userProfiles[position]
        holder.bind(userProfile)
    }

    override fun getItemCount(): Int {
        return userProfiles.size
    }

    inner class FoodViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userProfile: UserProfile) {
            // Set image and title (use proper image loading in a real application)
            binding.recipeImage.setImageResource(R.drawable.potato) // replace with actual image source
            binding.recipeTitle.text = userProfile.name

            binding.recipeImage.setOnClickListener {
                val intent = Intent(context, DeskripsiActivity::class.java).apply {
                    putExtra("EXTRA_DESCRIPTION", userProfile.description)
                }
                context.startActivity(intent)
            }
        }
    }
}
