package com.ssafy.birdmeal.view.market.shopping.order

import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentOrderBinding
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>(R.layout.fragment_order) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    override fun init() {

    }

}