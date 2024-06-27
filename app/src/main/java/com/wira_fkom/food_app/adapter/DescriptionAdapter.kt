package com.wira_fkom.food_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wira_fkom.food_app.databinding.ItemDescriptionBinding

class DescriptionAdapter(private val descriptions: List<String>) : RecyclerView.Adapter<DescriptionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(description: String) {
            binding.textViewItemDescription.text = description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(descriptions[position])
    }

    override fun getItemCount(): Int = descriptions.size

    fun getDescriptions(): List<String> {
        return descriptions
    }
}