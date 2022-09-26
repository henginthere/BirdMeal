package com.ssafy.birdmeal.view.market.shopping.order

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dttmm.web3test.wrapper.Trade
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentOrderBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>(R.layout.fragment_order) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        userViewModel.getUserTokenValue()

        binding.apply {
            shoppingVM = shoppingViewModel
            userVM = userViewModel
        }

        initClickListener()

        initViewModelCallBack()
    }

    private fun initViewModelCallBack(){
        shoppingViewModel.apply {
            // 주문 완료된 경우 주문완료 페이지로 이동
            orderSuccessMsgEvent.observe(viewLifecycleOwner){
                showToast(it)
                findNavController().navigate(R.id.action_orderFragment_to_orderCompletedFragment)
            }
        }
    }

    private fun initClickListener() = with(binding){
        // 뒤로가기 버튼 클릭
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        // 결제하기 버튼 클릭
        btnBuy.setOnClickListener {
            // 상품 컨트랙트 목록 불러오기
            val contractList : MutableList<Trade> = (requireActivity().application as ApplicationClass)
                    .getTradeContract(shoppingViewModel.productList.value)

            // 상품 컨트랙트 주문 넣기
            shoppingViewModel.buyingList(contractList, userViewModel.user.value!!.userSeq)
        }
    }

}