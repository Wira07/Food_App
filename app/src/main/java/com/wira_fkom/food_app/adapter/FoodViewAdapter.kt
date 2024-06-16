package com.wira_fkom.food_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wira_fkom.food_app.R
import com.wira_fkom.food_app.data.UserProfile
import com.wira_fkom.food_app.databinding.ItemRecipeBinding

class FoodViewAdapter(
    private val context: Context,
    private val profiles: List<UserProfile>
) : RecyclerView.Adapter<FoodViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }
    inner class ViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: UserProfile) {
            // Set image and text based on UserProfile data
            // For simplicity, using the same image for all items
            binding.recipeImage.setImageResource(R.drawable.potato)
            binding.recipeTitle.text = profile.fullName
        }
    }
}
