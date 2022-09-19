package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.SellerRemoteDataSource
import com.ssafy.birdmeal.model.dto.SellerDto
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SellerRepository @Inject constructor(
    private val sellerRemoteDataSource: SellerRemoteDataSource
){
    // 판매자 정보 조회
    fun getSellerInfo(sellerSeq: Int) : Flow<Result<BaseResponse<SellerDto>>> = flow {
        emit(Result.Loading)
        sellerRemoteDataSource.getSellerInfo(sellerSeq).collect {
            if(it.success){
                emit(Result.Success(it))
            }
            else {
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

}