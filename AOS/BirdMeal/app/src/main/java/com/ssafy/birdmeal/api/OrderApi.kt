package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.request.OrderRequestDto

import retrofit2.http.*
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import com.ssafy.birdmeal.model.response.OrderTHashResponse
import io.reactivex.Single

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

    //구매 확정하기
    @PUT("order")
    suspend fun updateOrderState(@Body request: OrderStateRequest): BaseResponse<String>

    //주문 상세 해시 불러오기
    @GET("order/detail/{order-detail-seq}")
    suspend fun getOrderTHash(@Path("order-detail-seq")orderDetailSeq: Int): BaseResponse<OrderTHashResponse>
}