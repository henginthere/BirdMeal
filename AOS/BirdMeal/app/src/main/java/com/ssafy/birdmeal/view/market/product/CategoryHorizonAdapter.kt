package com.ssafy.birdmeal.view.market.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemCategoryHorizonBinding
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.view.market.CategoryListener

class CategoryHorizonAdapter(private val listener: CategoryListener)
    : ListAdapter<CategoryDto, CategoryHorizonAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemCategoryHorizonBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition).categorySeq)
            }
        }

        fun bind(item : CategoryDto){
            binding.category = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryHorizonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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