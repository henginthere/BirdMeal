package com.ssafy.birdmeal.view.market.shopping.cart

import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentShoppingCartBinding
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCartFragment : BaseFragment<FragmentShoppingCartBinding>(R.layout.fragment_shopping_cart) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    override fun init() {
        shoppingViewModel.getCartList()

        initViewModelCallBack()
    }

    private fun initViewModelCallBack(){
        shoppingViewModel.errMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }
    }
}