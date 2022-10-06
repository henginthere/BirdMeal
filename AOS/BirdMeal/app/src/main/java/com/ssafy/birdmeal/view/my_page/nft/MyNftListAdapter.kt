package com.ssafy.birdmeal.view.my_page.nft

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemMyNftListBinding

class MyNftListAdapter() :
    ListAdapter<String, MyNftListAdapter.ViewHolder>(diffUtil) {

    lateinit var nftClickListener: NftClickListener

    inner class ViewHolder(private val binding: ItemMyNftListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) = with(binding) {
            imgUrl = data

            binding.root.setOnClickListener {
                nftClickListener.onClick(bindingAdapterPosition, data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyNftListBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}