package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.awesomedialog.*
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderDetailBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderAssumeDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderCancelDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderRefundDialog
import com.ssafy.birdmeal.view.my_page.OrderViewModel

class MyOrderDetailFragment :
    BaseFragment<FragmentMyOrderDetailBinding>(R.layout.fragment_my_order_detail) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    private var orderSeq = -1
    private val args by navArgs<MyOrderDetailFragmentArgs>()


    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)

        this.orderSeq = args.orderSeq

        orderViewModel.getOrderDetail(orderSeq)

        val adapter = OrderDetailListAdapter(listener)
        binding.apply {
            orderVM = orderViewModel
            rvOrderDetail.adapter = adapter
        }


        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() = with(orderViewModel) {
        orderMsgEvent.observe(viewLifecycleOwner) {
//            showToast(it)
        }

        // 상품 인수 끝
        loadingAssumeMsgEvent.observe(viewLifecycleOwner) {
            when (it) {
                ORDER_DETAIL_TO_STATE -> loadingOrderAssumeDialog.dismiss()
                ORDER_DETAIL_CANCEL -> loadingOrderCancelDialog.dismiss()
                ORDER_DETAIL_REFUND -> loadingOrderRefundDialog.dismiss()
            }

        }

        // 받은 seq로 상품 인수 시작
        orderToStateMsgEvent.observe(viewLifecycleOwner) {
            Log.d(TAG, "getOrderTHash: ${orderViewModel.orderDetailHash.value.productCa}")

            try {
                (requireActivity().application as ApplicationClass)
                    .getTradeContract(orderViewModel.orderDetailHash.value.productCa)
                orderStateDialog(it)
            } catch (e: Exception) {
                orderViewModel.setContractErr("getTradeContract")
                Log.d(TAG, "getTradeContract err: $e")
            }
        }

        // 받은 seq로 상품 취소 시작
        orderCancelMsgEvent.observe(viewLifecycleOwner) {

            try {
                (requireActivity().application as ApplicationClass)
                    .getTradeContract(orderViewModel.orderDetailHash.value.productCa)
                cancelDialog(it)
            } catch (e: Exception) {
                orderViewModel.setContractErr("getTradeContract")
                Log.d(TAG, "getTradeContract err: $e")
            }
        }

        // 받은 seq로 상품 환불 시작
        orderRefundMsgEvent.observe(viewLifecycleOwner) {

            try {
                (requireActivity().application as ApplicationClass)
                    .getTradeContract(orderViewModel.orderDetailHash.value.productCa)
                refundDialog(it)
            } catch (e: Exception) {
                orderViewModel.setContractErr("getTradeContract")
                Log.d(TAG, "getTradeContract err: $e")
            }
        }

    }

    private fun initClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val listener = object : OrderDetailListener {

        // 상품 인수
        override fun onStateClick(orderDetailSeq: Int) {
            orderViewModel.getOrderTHash(orderDetailSeq, ORDER_DETAIL_TO_STATE)
        }

        // 상품 취소
        override fun onCanceledClick(orderDetailSeq: Int) {
            orderViewModel.getOrderTHash(orderDetailSeq, ORDER_DETAIL_CANCEL)
        }

        // 상품 환불
        override fun onRefundClick(orderDetailSeq: Int) {
            orderViewModel.getOrderTHash(orderDetailSeq, ORDER_DETAIL_REFUND)
        }
    }

    private val dialogListener = object : CheckDialogListener {
        override fun onItemClick(orderDetailSeq: Int) { //구매확정처리
            loadingOrderAssumeDialog.show(childFragmentManager, "loadingAssumeDialog")

            val request = OrderStateRequest(orderDetailSeq, true)
            orderViewModel.updateOrderState(request)
        }
    }

    // 구매 확정 할건지 다이얼로그
    private fun orderStateDialog(orderDetailSeq: Int) {
        val dialog = CheckDialog(requireContext(), dialogListener, orderDetailSeq)
        dialog.show()
    }

    // 취소 할건지 다이얼로그
    private fun cancelDialog(orderDetailSeq: Int) {
        AwesomeDialog.build(requireActivity())
            .title("구매 취소")
            .body("구매를 취소하시겠습니까?")
            .icon(R.drawable.ic_duck)
            .onPositive(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {
                loadingOrderCancelDialog.show(childFragmentManager, "loadingAssumeDialog")
                orderViewModel.updateCancel(orderDetailSeq)
            }
            .onNegative(text = "취소", buttonBackgroundColor = R.drawable.btn_round_main_color) {

            }
    }

    // 환불 할건지 다이얼로그그
    private fun refundDialog(orderDetailSeq: Int) {
        AwesomeDialog.build(requireActivity())
            .title("환불")
            .body("물품을 환불하시겠습니까?")
            .icon(R.drawable.ic_duck)
            .onPositive(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {
                loadingOrderRefundDialog.show(childFragmentManager, "loadingAssumeDialog")
                orderViewModel.updateRefund(orderDetailSeq)
            }
            .onNegative(text = "취소", buttonBackgroundColor = R.drawable.btn_round_main_color) {

            }
    }
}