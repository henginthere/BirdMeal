package com.ssafy.birdmeal.view.donation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemDonationHistoryListBinding
import com.ssafy.birdmeal.model.dto.DonationHistoryDto

class DonationHistoryListAdapter() :
    ListAdapter<DonationHistoryDto, DonationHistoryListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemDonationHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DonationHistoryDto) = with(binding) {
            donationHistory = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDonationHistoryListBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<DonationHistoryDto>() {
            override fun areItemsTheSame(
                oldItem: DonationHistoryDto,
                newItem: DonationHistoryDto
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: DonationHistoryDto,
                newItem: DonationHistoryDto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}