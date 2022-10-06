package com.ssafy.birdmeal.model.request

data class OrderStateRequest (
    val orderDetailSeq: Int,
    val orderToState: Boolean
)