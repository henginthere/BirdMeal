package com.ssafy.birdmeal.view.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemCategoryGridBinding
import com.ssafy.birdmeal.model.dto.CategoryDto

class CategoryGridAdapter(private val listener: CategoryListener)
    : ListAdapter<CategoryDto, CategoryGridAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemCategoryGridBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition).categorySeq, getItem(absoluteAdapterPosition).categoryName)
            }
        }

        fun bind(item : CategoryDto){
            binding.category = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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