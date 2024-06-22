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
    private var userProfiles: List<UserProfile>
) : RecyclerView.Adapter<FoodViewAdapter.FoodViewHolder>() {

    private val favoriteList = mutableListOf<UserProfile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val userProfile = userProfiles[position]
        holder.bind(userProfile)
    }

    override fun getItemCount(): Int = userProfiles.size

    inner class FoodViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userProfile: UserProfile) {
            binding.recipeImage.setImageResource(userProfile.imageResId)
            binding.recipeTitle.text = userProfile.name

            binding.recipeImage.setOnClickListener {
                val intent = Intent(context, DeskripsiActivity::class.java).apply {
                    putStringArrayListExtra("EXTRA_DESCRIPTION", ArrayList(userProfile.description))
                    putExtra("EXTRA_IMAGE_RES_ID", userProfile.imageResId)
                }
                context.startActivity(intent)
            }

            binding.favoriteButton.setOnClickListener {
                if (favoriteList.contains(userProfile)) {
                    favoriteList.remove(userProfile)
                    binding.favoriteButton.setImageResource(R.drawable.ic_favorite)
                } else {
                    favoriteList.add(userProfile)
                    binding.favoriteButton.setImageResource(R.drawable.ic_favorite)
                }
            }
        }
    }

    fun getFavorites(): List<UserProfile> = favoriteList

    fun updateData(newProfiles: List<UserProfile>) {
        userProfiles = newProfiles
        notifyDataSetChanged()
    }
}