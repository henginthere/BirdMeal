package com.ssafy.birdmeal.view.donation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemChildHistoryListBinding
import com.ssafy.birdmeal.databinding.ItemDonationHistoryListBinding
import com.ssafy.birdmeal.model.response.ChildHistoryResponse

class ChildHistoryListAdapter() :
    ListAdapter<ChildHistoryResponse, ChildHistoryListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemChildHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ChildHistoryResponse) = with(binding) {
            childHistory = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChildHistoryListBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<ChildHistoryResponse>() {
            override fun areItemsTheSame(
                oldItem: ChildHistoryResponse,
                newItem: ChildHistoryResponse
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: ChildHistoryResponse,
                newItem: ChildHistoryResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}