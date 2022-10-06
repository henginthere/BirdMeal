package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.model.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    // 상품 카테고리 목록 조회
    @GET("product/category-list")
    suspend fun getCategoryList() : BaseResponse<List<CategoryDto>>

    // 카테고리 내 상품 목록 조회
    @GET("product/{category-seq}/list")
    suspend fun getProductList(
        @Path("category-seq") categorySeq: Int
    ) : BaseResponse<List<ProductDto>>

    // 상품 상세정보 조회
    @GET("product/{product-seq}")
    suspend fun getProduct(
        @Path("product-seq") productSeq: Int
    ) : BaseResponse<ProductDto>

    // 상품 검색
    @GET("product/search/{product-search-name}")
    suspend fun searchProduct(
        @Path("product-search-name") name: String
    ) : BaseResponse<List<ProductDto>>
}