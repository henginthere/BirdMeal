package com.ssafy.birdmeal.view.market.product.detail

import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentSellerDetailBinding

class SellerDetailFragment : BaseFragment<FragmentSellerDetailBinding>(R.layout.fragment_seller_detail) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.ivShoppingCart.setOnClickListener {
            findNavController().navigate(R.id.action_sellerDetailFragment_to_shoppingCartFragment)
        }
    }

}