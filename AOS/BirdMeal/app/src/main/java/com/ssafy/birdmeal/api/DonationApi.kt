package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.model.response.ChildHistoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DonationApi {

    // 기부 내역 저장
    @POST("donation")
    suspend fun insertDonationHistory(@Body donationHistory: DonationHistoryDto): BaseResponse<Boolean>

    // 전체 기부 내역 불러오기
    @GET("donation")
    suspend fun getAllDonationHistory(): BaseResponse<List<DonationHistoryDto>>

    // 내 기부 내역 불러오기기
    @GET("donation/{user-seq}")
    suspend fun getMyDonationHistory(@Path("user-seq") userSeq: Int): BaseResponse<List<DonationHistoryDto>>

    // 아이들 주문 내역 불러오기
    @GET("order/child/list")
    suspend fun getChildOrderHistory(): BaseResponse<List<ChildHistoryResponse>>
}