package com.ssafy.birdmeal.view.my_page.history.order.detail

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderDetailBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
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
            showToast(it)
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
            initCheckDialog(orderDetailSeq)
            (requireActivity().application as ApplicationClass)
                .getTradeContract(orderViewModel.orderDetailHash.value.productCa)

        }
    }

    private val dialogListener = object : CheckDialogListener {
        override fun onItemClick(orderDetailSeq: Int) { //구매확정처리
            val request = OrderStateRequest(orderDetailSeq, true)
            orderViewModel.updateOrderState(request)
            showToast("구매 확정을 하였습니다.")
        }
    }

    private fun initCheckDialog(orderDetailSeq: Int) {
        orderViewModel.getOrderTHash(orderDetailSeq)
        val dialog = CheckDialog(requireContext(), dialogListener, orderDetailSeq)
        dialog.show()
    }

}