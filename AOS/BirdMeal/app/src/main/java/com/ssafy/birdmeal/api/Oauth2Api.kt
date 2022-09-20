package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.JoinRequest
import com.ssafy.birdmeal.model.response.OauthResponse
import retrofit2.http.*

interface Oauth2Api {

    // 로그인 요청
    @POST("user/login")
    suspend fun googleLogin(@Body map: Map<String, String>): BaseResponse<OauthResponse>

    // 회원가입 요청
    @POST("user/register")
    suspend fun join(@Body request: JoinRequest): BaseResponse<OauthResponse>

    // 결식카드 인증 요청
    @POST("user/check-child")
    suspend fun checkCard(@Body map: Map<String, String>): BaseResponse<Boolean>
}