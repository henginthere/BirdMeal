package com.ssafy.birdmeal.view.market.shopping.cart

import com.ssafy.birdmeal.model.entity.CartEntity

interface ShoppingCartListener {
    fun onDeleteClick(cart: CartEntity)
}