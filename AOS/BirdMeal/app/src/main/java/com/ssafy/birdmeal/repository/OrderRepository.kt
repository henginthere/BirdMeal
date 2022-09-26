package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.OrderRemoteDataSource
import com.ssafy.birdmeal.model.request.OrderRequestDto
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
    // 주문 내역 저장하기
    fun createOrderList(orderRequestDtoList: List<OrderRequestDto>): Flow<Result<BaseResponse<Any>>> = flow {
        emit(Result.Uninitialized)
        orderRemoteDataSource.createOrderList(orderRequestDtoList).collect {
            if(it.success){
                emit(Result.Success(it))
            }
            else if(!it.success){
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
    // 내 주문 내역 불러오기
    fun getMyOrderHistory(userSeq:Int): Flow<Result<BaseResponse<List<OrderResponse>>>> = flow {
        emit(Result.Loading)
        orderRemoteDataSource.getMyOrderHistory(userSeq).collect {
            if(it.success){
                emit(Result.Success(it))
            }
            else if(!it.success){
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
    // 주문 상세 내역 불러오기
    fun getOrderDetail(userSeq:Int,orderSeq:Int): Flow<Result<BaseResponse<List<OrderDetailResponse>>>> = flow {
        emit(Result.Loading)
        orderRemoteDataSource.getOrderDetail(userSeq,orderSeq).collect {
            if(it.success){
                emit(Result.Success(it))
            }
            else if(!it.success){
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}