package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    // 회원정보 불러오기
    @GET("userInfo/{user-seq}")
    suspend fun getUserInfo(@Path("user-seq") userSeq: Int): BaseResponse<UserDto>
}