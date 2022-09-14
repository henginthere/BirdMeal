package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.model.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductApi {

    @GET("product/category-list")
    suspend fun getCategoryList() : BaseResponse<List<CategoryDto>>

    @GET("product/{category-seq}/list")
    suspend fun getProductList(
        @Path("category-seq") categorySeq: Int
    ) : BaseResponse<List<ProductDto>>

}