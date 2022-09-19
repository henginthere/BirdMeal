package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.SellerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SellerApi {
    // 판매자 정보 조회
    @GET("seller/{seller-seq}")
    suspend fun getSellerInfo(
        @Path("seller-seq") sellerSeq: Int
    ) : BaseResponse<SellerDto>

}