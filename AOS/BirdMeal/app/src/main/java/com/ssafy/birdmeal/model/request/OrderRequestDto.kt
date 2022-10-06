package com.ssafy.birdmeal.model.request

data class OrderRequestDto(
    val userSeq: Int,
    val orderQuantity: Int,
    val orderTHash: String,
    val productSeq: Int
)