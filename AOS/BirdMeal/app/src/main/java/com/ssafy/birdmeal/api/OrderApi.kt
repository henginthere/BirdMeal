package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.model.request.OrderRequestDto
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {

    // 주문 내역 저장하기
    @POST("order")
    suspend fun createOrderList(
        @Body orderRequestDtoList: List<OrderRequestDto>
    ): BaseResponse<Any>

    // 내 주문 내역 불러오기
    @GET("order/list/{user-seq}")
    suspend fun getMyOrderHistory(@Path("user-seq") userSeq: Int): BaseResponse<List<OrderResponse>>

    // 주문 상세 내역 불러오기
    @GET("order/list/{user-seq}/{order-seq}")
    suspend fun getOrderDetail(@Path("user-seq") userSeq: Int, @Path("order-seq") orderSeq: Int): BaseResponse<List<OrderDetailResponse>>

}