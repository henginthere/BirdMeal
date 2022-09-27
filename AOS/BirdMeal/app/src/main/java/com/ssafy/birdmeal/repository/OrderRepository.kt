package com.ssafy.birdmeal.repository

import android.util.Log
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.OrderRemoteDataSource
import com.ssafy.birdmeal.model.request.OrderRequestDto
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import com.ssafy.birdmeal.model.response.OrderTHashResponse
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.TAG
import io.reactivex.Single
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

    //상품 인수하기
    fun updateOrderState(request: OrderStateRequest): Flow<Result<BaseResponse<String>>> = flow {
        emit(Result.Loading)
        orderRemoteDataSource.updateOrderState(request).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    //주문 해시 불러오기
    fun getOrderTHash(orderDetailSeq:Int) : Flow<Result<BaseResponse<OrderTHashResponse>>> = flow {
        emit(Result.Loading)
        orderRemoteDataSource.getOrderTHash(orderDetailSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}