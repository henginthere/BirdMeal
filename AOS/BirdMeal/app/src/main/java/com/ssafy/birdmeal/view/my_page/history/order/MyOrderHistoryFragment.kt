package com.ssafy.birdmeal.view.my_page.history.order

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderHistoryBinding
import com.ssafy.birdmeal.view.donation.history.DonationHistoryListAdapter
import com.ssafy.birdmeal.view.my_page.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderHistoryFragment : BaseFragment<FragmentMyOrderHistoryBinding>(R.layout.fragment_my_order_history) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    private val orderHistoryListAdapter by lazy { OrderHistoryListAdapter() }

    override fun init() {
        binding.orderVM = orderViewModel
        orderViewModel.getMyOrderHistory()
        initAdapter()
        initViewModelCallBack()
//        initClickListener()
    }
    private fun initAdapter() = with(binding) {
        rvOrderHistory.adapter = orderHistoryListAdapter
    }

    private fun initViewModelCallBack() = with(orderViewModel) {
        orderMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

//    private fun initClickListener() = with(binding) {
//        // 주문상세 내역 보기
//        btnOrderHistory.setOnClickListener {
//            findNavController().navigate(R.id.action_donationFragment_to_donorHistoryFragment)
//        }
//
//    }
}