package com.ssafy.birdmeal.view.my_page.history.order.detail

interface OrderDetailListener {
    fun onStateClick(orderDetailSeq: Int)   // 상품 인수 클릭
    fun onCanceledClick(orderDetailSeq: Int) // 상품 취소 클릭
    fun onRefundClick(orderDetailSeq: Int) // 상품 환불 클릭
}