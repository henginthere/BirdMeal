package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.ProductApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.model.dto.ProductDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    // 상품 카테고리 목록 조회
    fun getCategoryList() : Flow<BaseResponse<List<CategoryDto>>> = flow {
        emit(productApi.getCategoryList())
    }

    // 카테고리 내 상품 전체 목록 조회
    fun getProductList(categorySeq: Int) : Flow<BaseResponse<List<ProductDto>>> = flow {
        emit(productApi.getProductList(categorySeq))
    }

    // 상품 상세정보 조회
    fun getProduct(productSeq: Int) : Flow<BaseResponse<ProductDto>> = flow {
        emit(productApi.getProduct(productSeq))
    }

}