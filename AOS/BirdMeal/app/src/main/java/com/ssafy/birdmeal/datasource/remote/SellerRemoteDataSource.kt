package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.SellerApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.ProductDto
import com.ssafy.birdmeal.model.dto.SellerDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SellerRemoteDataSource @Inject constructor(
    private val sellerApi: SellerApi
) {

    // 판매자 정보 조회
    fun getSellerInfo(sellerSeq: Int) : Flow<BaseResponse<SellerDto>> = flow {
        emit(sellerApi.getSellerInfo(sellerSeq))
    }

    // 판매자 등록 상품 목록 조회
    fun getSellerProducts(sellerSeq: Int) : Flow<BaseResponse<List<ProductDto>>> = flow {
        emit(sellerApi.getSellerProducts(sellerSeq))
    }

}