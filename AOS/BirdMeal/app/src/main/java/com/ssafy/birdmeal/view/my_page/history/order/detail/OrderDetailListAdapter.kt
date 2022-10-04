package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.ItemOrderDetailListBinding
import com.ssafy.birdmeal.model.response.OrderDetailResponse


class OrderDetailListAdapter(private val listener: OrderDetailListener) :
    ListAdapter<OrderDetailResponse, OrderDetailListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemOrderDetailListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivOrderState.setOnClickListener {
                listener.onStateClick(getItem(absoluteAdapterPosition).orderDetailSeq)
            }
        }

        fun bind(item: OrderDetailResponse) = with(binding) {
            binding.orderDetail = item
            binding.executePendingBindings()

            // 배송 준비중
            if (item.orderDeliveryCompany == null && item.orderDeliveryNumber == null) {
                tvDeliveryCompany.text = "배송 준비중"
                tvDeliveryNumber.text = ""

                ivOrderState.background =
                    itemView.context.resources.getDrawable(R.drawable.btn_round_gray_no_stroke)
                ivOrderState.setTextColor(itemView.context.resources.getColor(R.color.black_low_emphasis))

                ivOrderState.text = "인수 확인"
                ivOrderState.setClickable(false)

            } else {
                // 구매확정 이미 했을 때
                if (item.orderToState) {
                    ivOrderState.background =
                        itemView.context.resources.getDrawable(R.drawable.background_transparent)
                    ivOrderState.setTextColor(itemView.context.resources.getColor(R.color.black_low_emphasis))

                    ivOrderState.text = "인수 완료"
                    ivOrderState.setClickable(false)
                }
                // 구매확정 가능할 때
                else if (!item.orderToState) {
                    ivOrderState.background =
                        itemView.context.resources.getDrawable(R.drawable.btn_round_green_color)
                    ivOrderState.setTextColor(itemView.context.resources.getColor(R.color.white))

                    ivOrderState.text = "인수 확인"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderDetailListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OrderDetailResponse>() {
            override fun areItemsTheSame(
                oldItem: OrderDetailResponse,
                newItem: OrderDetailResponse
            ): Boolean {
                return oldItem.orderDetailSeq == newItem.orderDetailSeq
            }

            override fun areContentsTheSame(
                oldItem: OrderDetailResponse,
                newItem: OrderDetailResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}