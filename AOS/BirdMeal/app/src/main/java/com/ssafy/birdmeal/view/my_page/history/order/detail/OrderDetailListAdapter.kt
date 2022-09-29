package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.ItemOrderDetailListBinding
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.view.my_page.OrderViewModel


class OrderDetailListAdapter(private val listener: OrderDetailListener)
    : ListAdapter<OrderDetailResponse, OrderDetailListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding : ItemOrderDetailListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivOrderState.setOnClickListener{
                listener.onStateClick(getItem(absoluteAdapterPosition).orderDetailSeq)
            }
        }

        fun bind(item : OrderDetailResponse){
            binding.orderDetail = item
            binding.executePendingBindings()
            if(item.orderDeliveryCompany==null&&item.orderDeliveryNumber==null){
                binding.tvDeliveryCompany.text = "택배사 정보가 입력되지 않았습니다."
                binding.tvDeliveryNumber.text = "운송장 번호가 입력되지 않았습니다."
            }

            if(item.orderToState||item.orderDeliveryCompany==null&&item.orderDeliveryNumber==null){
                binding.ivOrderState.setImageResource(R.drawable.btn_order_state_true)
                binding.ivOrderState.setClickable(false)
            }
            else if(!item.orderToState){
                binding.ivOrderState.setImageResource(R.drawable.btn_order_state_false)
            }



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