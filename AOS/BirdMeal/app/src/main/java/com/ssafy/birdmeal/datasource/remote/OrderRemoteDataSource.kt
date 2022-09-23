package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.OrderApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.model.response.OrderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val orderApi: OrderApi
) {
    fun getMyOrderHistory(userSeq: Int): Flow<BaseResponse<List<OrderResponse>>> = flow {
        emit(orderApi.getMyOrderHistory(userSeq))
    }
}