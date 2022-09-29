package com.ssafy.birdmeal.view.market.shopping.order

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentOrderCompletedBinding
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderCompletedFragment : BaseFragment<FragmentOrderCompletedBinding>(R.layout.fragment_order_completed) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        binding.apply {
            shoppingVM = shoppingViewModel
            userVM = userViewModel
        }

        userViewModel.getUserInfo()
        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() = with(binding){
        btnBack.setOnClickListener { // 마켓 카테고리 화면으로 이동
            findNavController().navigate(R.id.action_orderCompletedFragment_to_categoryFragment)
        }
    }

    private fun initViewModelCallBack()= with(userViewModel) {

        user.observe(viewLifecycleOwner) {
            binding.tvAddressValue.text = it.userAdd
        }

    }

}