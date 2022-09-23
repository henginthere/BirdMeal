package com.ssafy.birdmeal.view.my_page.history.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemOrderHistoryListBinding
import com.ssafy.birdmeal.model.response.OrderResponse

class OrderHistoryListAdapter():
    ListAdapter<OrderResponse, OrderHistoryListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemOrderHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: OrderResponse) = with(binding) {
            orderHistory = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderHistoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OrderResponse>() {
            override fun areItemsTheSame(
                oldItem: OrderResponse,
                newItem: OrderResponse
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: OrderResponse,
                newItem: OrderResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}