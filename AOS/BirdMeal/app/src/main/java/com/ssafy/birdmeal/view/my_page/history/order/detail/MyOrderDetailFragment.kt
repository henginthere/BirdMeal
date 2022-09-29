package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyOrderDetailBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.market.CategoryListener
import com.ssafy.birdmeal.view.market.MarketViewModel
import com.ssafy.birdmeal.view.market.product.ProductListFragmentArgs
import com.ssafy.birdmeal.view.market.product.detail.BuyBottomSheetDialog
import com.ssafy.birdmeal.view.market.product.detail.ProductDetailFragmentArgs
import com.ssafy.birdmeal.view.market.shopping.cart.ProductCntDialog
import com.ssafy.birdmeal.view.market.shopping.cart.ProductCntDialogListener
import com.ssafy.birdmeal.view.market.shopping.cart.ShoppingCartListener
import com.ssafy.birdmeal.view.my_page.OrderViewModel
import com.ssafy.birdmeal.view.my_page.history.order.OrderHistoryListAdapter
import com.ssafy.birdmeal.view.my_page.history.order.OrderListener

class MyOrderDetailFragment : BaseFragment<FragmentMyOrderDetailBinding>(R.layout.fragment_my_order_detail) {

    private val orderViewModel by activityViewModels<OrderViewModel>()
    private var orderSeq = -1
    private val args by navArgs<MyOrderDetailFragmentArgs>()


    override fun init() {
        this.orderSeq = args.orderSeq
        orderViewModel.getOrderDetail(orderSeq)
        val adapter = OrderDetailListAdapter(listener)
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
            val request = OrderStateRequest(orderDetailSeq,true)
            orderViewModel.updateOrderState(request)
            showToast("구매 확정을 하였습니다.")
        }
    }

    private fun initCheckDialog(orderDetailSeq: Int){
        orderViewModel.getOrderTHash(orderDetailSeq)
        val dialog = CheckDialog(requireContext(),dialogListener, orderDetailSeq)
        dialog.show()
    }

}