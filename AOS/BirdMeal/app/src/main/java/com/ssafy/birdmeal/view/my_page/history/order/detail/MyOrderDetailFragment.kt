package com.ssafy.birdmeal.view.my_page.history.order.detail

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderDetailBinding
import com.ssafy.birdmeal.view.market.MarketViewModel
import com.ssafy.birdmeal.view.market.product.ProductListFragmentArgs
import com.ssafy.birdmeal.view.market.product.detail.ProductDetailFragmentArgs
import com.ssafy.birdmeal.view.my_page.OrderViewModel
import com.ssafy.birdmeal.view.my_page.history.order.OrderHistoryListAdapter

class MyOrderDetailFragment : BaseFragment<FragmentMyOrderDetailBinding>(R.layout.fragment_my_order_detail) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    private var orderSeq = -1
    private val args by navArgs<MyOrderDetailFragmentArgs>()

    override fun init() {
        this.orderSeq = args.orderSeq

        orderViewModel.getOrderDetail(orderSeq)
        val adapter = OrderDetailListAdapter()
        binding.apply {
            orderVM = orderViewModel
            rvOrderDetail.adapter = adapter
        }

        initViewModelCallBack()
    }

    private fun initViewModelCallBack() = with(orderViewModel) {
        orderMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

}