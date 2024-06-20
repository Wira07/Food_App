package com.wira_fkom.food_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wira_fkom.food_app.databinding.ItemDescriptionBinding

class DescriptionAdapter(private val descriptions: List<String>) :
    RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val binding = ItemDescriptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
        holder.bind(descriptions[position], position)
    }

    override fun getItemCount(): Int = descriptions.size

    inner class DescriptionViewHolder(private val binding: ItemDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(description: String, position: Int) {
            binding.textViewIndex.text = "${position + 1}."
            binding.textViewItemDescription.text = description
        }
    }
}
