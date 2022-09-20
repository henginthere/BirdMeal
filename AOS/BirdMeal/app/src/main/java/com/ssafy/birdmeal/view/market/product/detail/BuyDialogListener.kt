package com.ssafy.birdmeal.view.market.product.detail

import com.ssafy.birdmeal.model.entity.CartEntity

interface BuyDialogListener {
    fun onItemClick(cart: CartEntity)
}