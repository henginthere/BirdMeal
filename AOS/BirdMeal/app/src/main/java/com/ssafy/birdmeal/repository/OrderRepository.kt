package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.OrderRemoteDataSource
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderRemoteDataSource: OrderRemoteDataSource
){
    fun getMyOrderHistory(userSeq:Int): Flow<Result<BaseResponse<List<OrderResponse>>>> = flow {
        emit(Result.Loading)
        orderRemoteDataSource.getMyOrderHistory(userSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun getOrderDetail(userSeq:Int,orderSeq:Int): Flow<Result<BaseResponse<List<OrderDetailResponse>>>> = flow {
        emit(Result.Loading)
        orderRemoteDataSource.getOrderDetail(userSeq,orderSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}