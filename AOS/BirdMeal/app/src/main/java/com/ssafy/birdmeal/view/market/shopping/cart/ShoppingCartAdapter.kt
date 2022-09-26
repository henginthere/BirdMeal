package com.ssafy.birdmeal.view.market.shopping.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemCartListBinding
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.utils.TAG

class ShoppingCartAdapter(private val listener: ShoppingCartListener)
    : ListAdapter<CartEntity, ShoppingCartAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding : ItemCartListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                ivDelete.setOnClickListener { // 삭제버튼 클릭
                    listener.onDeleteClick(getItem(adapterPosition))
                }
                btnCnt.setOnClickListener { // 수량버튼 클릭
                    listener.onCntClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(item : CartEntity){
            Log.d(TAG, "bind: ${item.productCount}")
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
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}

