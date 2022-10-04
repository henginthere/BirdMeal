package com.ssafy.birdmeal.view.my_page.history.order

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderHistoryBinding
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.my_page.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderHistoryFragment : BaseFragment<FragmentMyOrderHistoryBinding>(R.layout.fragment_my_order_history) {

    private val orderViewModel by activityViewModels<OrderViewModel>()

    override fun init() {

        orderViewModel.getMyOrderHistory()
        val adapter = OrderHistoryListAdapter(orderListener)

        binding.apply {
            orderVM = orderViewModel
            rvOrderHistory.adapter = adapter
        }

        initViewModelCallBack()
    }


    private fun initViewModelCallBack() = with(orderViewModel) {
        orderMsgEvent.observe(viewLifecycleOwner) {
//            showToast(it)
        }
    }

    private val orderListener = object : OrderListener {
        override fun onItemClick(userSeq: Int) {  //  주문 상세 내역으로 이동
            Log.d(TAG, "getMyOrderHistory data: $userSeq")
            val action = MyOrderHistoryFragmentDirections.actionMyOrderHistoryFragmentToMyOrderDetailFragment(userSeq)
            findNavController().navigate(action)
        }
    }
}