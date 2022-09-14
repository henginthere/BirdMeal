package com.ssafy.birdmeal.view.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemCategoryBinding
import com.ssafy.birdmeal.model.dto.CategoryDto

class CategoryAdapter : ListAdapter<CategoryDto, CategoryAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : CategoryDto){
            binding.category = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CategoryDto>(){
            override fun areItemsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
                return oldItem.categorySeq == newItem.categorySeq
            }

            override fun areContentsTheSame(oldItem: CategoryDto, newItem: CategoryDto): Boolean {
                return oldItem == newItem
            }
        }
    }

}