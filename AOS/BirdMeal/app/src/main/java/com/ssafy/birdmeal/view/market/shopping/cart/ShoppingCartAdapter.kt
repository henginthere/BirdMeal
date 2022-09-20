package com.ssafy.birdmeal.view.market.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemCartListBinding
import com.ssafy.birdmeal.model.entity.CartEntity

class ShoppingCartAdapter
    : ListAdapter<CartEntity, ShoppingCartAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemCartListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : CartEntity){
            binding.cart = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CartEntity>(){
            override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
                return oldItem.productSeq == newItem.productSeq
            }

            override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}

