package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.EOARequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    // 회원정보 불러오기
    @GET("user/{user-seq}/info")
    suspend fun getUserInfo(@Path("user-seq") userSeq: Int): BaseResponse<UserDto>

    // 회원 EOA 업데이트
    @PUT("user/wallet")
    suspend fun updateUserEOA(@Body request: EOARequest): BaseResponse<String>

    // 회원 정보 업데이트
    @PUT("user")
    suspend fun updateUserProfile(
        @Body map: Map<String, String>
    ): BaseResponse<String>

    // 결식 아동 충전 상태 변경
    @PUT("user/check-child/{user-seq}")
    suspend fun updateChildState(@Path("user-seq") userSeq: Int): BaseResponse<Any>
}