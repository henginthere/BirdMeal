package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderApi {

    // 내 주문 내역 불러오기
    @GET("order/list/{user-seq}")
    suspend fun getMyOrderHistory(@Path("user-seq") userSeq: Int): BaseResponse<List<OrderResponse>>

//    // 주문 상세 내역 불러오기
//    @GET("order/list/{user-seq}/{order-seq}")
//    suspend fun getMyOrderDetail(@Path("user-seq") userSeq: Int): BaseResponse<List<DonationHistoryDto>>

}