package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.OrderApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.request.OrderRequestDto
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val orderApi: OrderApi
) {

    // 주문 내역 저장하기
    fun createOrderList(orderRequestDtoList: List<OrderRequestDto>): Flow<BaseResponse<Any>> = flow {
        emit(orderApi.createOrderList(orderRequestDtoList))
    }
    // 내 주문내역 불러오기
    fun getMyOrderHistory(userSeq: Int): Flow<BaseResponse<List<OrderResponse>>> = flow {
        emit(orderApi.getMyOrderHistory(userSeq))
    }
    // 주문 상세내역 불러오기
    fun getOrderDetail(userSeq: Int,orderSeq: Int): Flow<BaseResponse<List<OrderDetailResponse>>> = flow {
        emit(orderApi.getOrderDetail(userSeq,orderSeq))
    }

    fun updateOrderState(request: OrderStateRequest):Flow<BaseResponse<String>> = flow{
        emit(orderApi.updateOrderState(request))
    }

}