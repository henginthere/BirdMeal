package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemOrderDetailListBinding
import com.ssafy.birdmeal.model.response.OrderDetailResponse



class OrderDetailListAdapter()
    : ListAdapter<OrderDetailResponse, OrderDetailListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding : ItemOrderDetailListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : OrderDetailResponse){
            binding.orderDetail = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderDetailListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OrderDetailResponse>(){
            override fun areItemsTheSame(oldItem: OrderDetailResponse, newItem: OrderDetailResponse): Boolean {
                return oldItem.orderDetailSeq == newItem.orderDetailSeq
            }

            override fun areContentsTheSame(oldItem: OrderDetailResponse, newItem: OrderDetailResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}