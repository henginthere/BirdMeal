package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.birdmeal.databinding.ItemOrderDetailListBinding
import com.ssafy.birdmeal.model.response.OrderDetailResponse


class OrderDetailListAdapter(private val listener: OrderDetailListener) :
    ListAdapter<OrderDetailResponse, OrderDetailListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemOrderDetailListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderDetailResponse) = with(binding) {
            orderDetail = item
            executePendingBindings()

            btnOrderState.setOnClickListener {
                listener.onStateClick(item.orderDetailSeq)
            }
            btnCancel.setOnClickListener {
                listener.onCanceledClick(item.orderDetailSeq)
            }
            btnRefund.setOnClickListener {
                listener.onRefundClick(item.orderDetailSeq)
            }

            // 삭제 X -> 환불은 항상 비활성화
            if (!item.productIsDeleted) {
                btnRefund.visibility = View.GONE

                // 삭제 X 취소 X
                if (!item.orderIsCanceled) {
                    // 삭제 X 취소 X 인수 X
                    if (!item.orderToState) {
                        // 삭제 X 취소 X 인수 X 배송 X
                        if (item.orderDeliveryCompany == null) {
                            /*
                            삭제 X 취소 X 환불 X 인수 X 배송 X
                            아직 고객이 상품을 인수받기전이고 배송을 시작하지 않은 평범한케이스
                            주문취소버튼 활성화
                             */
                            btnCancel.visibility = View.VISIBLE
                            btnOrderState.visibility = View.GONE

                            tvHeader.text = "배송 준비중인 상품입니다"
                        }
                        // 삭제 X 취소 X 인수 X 배송 O
                        else {
                            /*
                            삭제 X 취소 X 환불 X 인수 X 배송 O
                            아직 고객이 상품을 인수받기전이고 배송을 시작한 평범한케이스
                            인수버튼 활성화
                            주문취소버튼 활성화
                             */
                            btnCancel.visibility = View.VISIBLE
                            btnOrderState.visibility = View.VISIBLE

                            tvHeader.text = "배송 중입니다"
                        }
                    }
                    // 삭제 X 취소 X 인수 O
                    else {
                        /*
                        삭제 X 취소 X 환불 X 인수 O (인수버튼을 누른 상태)
                        아무버튼도 활성화 x 보여주는것도 그냥 기존과 똑같이 평범하게
                         */
                        btnCancel.visibility = View.GONE
                        btnOrderState.visibility = View.GONE

                        tvHeader.text = "구매 확정이 완료되었습니다"
                    }
                }
                // 삭제 X 취소 O
                else {
                    /*
                    삭제 X 취소 O 인수 X  (취소버튼을 눌렀을때 일어나야 하는 일)
                    토큰컨트랙트.approve 선행 후
                    TradeContract.refund(트랜잭션해쉬) -> 컨트랙트로부터 주문자 계좌로 돈 환불 될거임
                    api호출(isCancled를 true로 바꾸는 함수 호출)

                    이 이후에 이 주문은 취소하신 상품입니다.(아무버튼도 활성화 x)
                     */
                    btnCancel.visibility = View.GONE
                    btnOrderState.visibility = View.GONE

                    tvHeader.text = "주문 취소한 상품입니다"
                }
            }
            // 삭제 O
            else {
                btnCancel.visibility = View.GONE
                btnOrderState.visibility = View.GONE
                // 삭제 O 환불 X
                if (!item.orderIsRefunded) {

                    // 삭제 O 취소 O || 인수 O
                    if (item.orderIsCanceled || item.orderToState) {
                        btnRefund.visibility = View.GONE

                        tvHeader.text = "더이상 판매하지 않는 상품입니다"
                    } else {
                        /*
                    삭제 O 환불 X  (판매자가 상품을 삭제해버렸어)
                    판매자가 상품을 내렸습니다. 환불 버튼 활성화
                    -> 환불버튼을 누르겠지?
                    토큰컨트랙트.approve 선행 후
                    TradeContract.refund(트랜잭션해쉬) -> 컨트랙트로부터 주문자 계좌로 돈 환불 될거임
                    api호출(isRefund를 true로 바꾸는 함수 호출)
                     */
                        btnRefund.visibility = View.VISIBLE

                        tvHeader.text = "판매자가 판매를 취소한 물품입니다"
                    }
                }
                // 삭제 O 환불 O
                else {
                    /*
                    삭제 O 환불 O (위의 로직 실행 후 환불을 받은 상태)
                    버튼 전부 비활성화
                    판매자가 상품을 내렸습니다.
                     */
                    btnRefund.visibility = View.GONE

                    tvHeader.text = "환불된 물품입니다"
                }
            }


//            // 배송 준비중
//            if (item.orderDeliveryCompany == null && item.orderDeliveryNumber == null) {
//                tvDeliveryCompany.text = "배송 준비중"
//                tvDeliveryNumber.text = ""
//
//                ivOrderState.background =
//                    itemView.context.resources.getDrawable(R.drawable.btn_round_gray_no_stroke)
//                ivOrderState.setTextColor(itemView.context.resources.getColor(R.color.black_low_emphasis))
//
//                ivOrderState.text = "인수 확인"
//                ivOrderState.setClickable(false)
//
//            } else {
//                // 구매확정 이미 했을 때
//                if (item.orderToState) {
//                    ivOrderState.background =
//                        itemView.context.resources.getDrawable(R.drawable.background_transparent)
//                    ivOrderState.setTextColor(itemView.context.resources.getColor(R.color.black_low_emphasis))
//
//                    ivOrderState.text = "인수 완료"
//                    ivOrderState.setClickable(false)
//                }
//                // 구매확정 가능할 때
//                else if (!item.orderToState) {
//                    ivOrderState.background =
//                        itemView.context.resources.getDrawable(R.drawable.btn_round_green_color)
//                    ivOrderState.setTextColor(itemView.context.resources.getColor(R.color.white))
//
//                    ivOrderState.text = "인수 확인"
//                }
//            }
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