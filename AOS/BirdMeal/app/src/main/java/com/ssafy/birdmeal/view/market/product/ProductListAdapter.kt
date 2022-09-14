package com.ssafy.birdmeal.view.market.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemProdcutListBinding
import com.ssafy.birdmeal.model.dto.ProductDto

class ProductListAdapter(private val listener: ProductListener)
    : ListAdapter<ProductDto, ProductListAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemProdcutListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition).productSeq)
            }
        }

        fun bind(item : ProductDto){
            binding.product = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProdcutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductDto>(){
            override fun areItemsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
                return oldItem.productSeq == newItem.productSeq
            }

            override fun areContentsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
                return oldItem == newItem
            }
        }
    }

}