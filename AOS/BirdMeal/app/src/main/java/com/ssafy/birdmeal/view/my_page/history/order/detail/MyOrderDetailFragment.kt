package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderDetailBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingAssumeDialog
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
            loadingAssumeDialog.dismiss()
        }

        // 해당 제품의 해시값 불러오기 성공
        orderDetailSeqMsgEvent.observe(viewLifecycleOwner) {
            Log.d(TAG, "getOrderTHash: ${orderViewModel.orderDetailHash.value.productCa}")

            try {
                (requireActivity().application as ApplicationClass)
                    .getTradeContract(orderViewModel.orderDetailHash.value.productCa)
                initCheckDialog(it)
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

        override fun onStateClick(orderDetailSeq: Int) {
            orderViewModel.getOrderTHash(orderDetailSeq)
        }
    }

    private val dialogListener = object : CheckDialogListener {
        override fun onItemClick(orderDetailSeq: Int) { //구매확정처리
            loadingAssumeDialog.show(childFragmentManager, "loadingAssumeDialog")

            val request = OrderStateRequest(orderDetailSeq, true)
            orderViewModel.updateOrderState(request)
            showToast("구매 확정을 하였습니다.")
        }
    }

    private fun initCheckDialog(orderDetailSeq: Int) {
        val dialog = CheckDialog(requireContext(), dialogListener, orderDetailSeq)
        dialog.show()
    }

}