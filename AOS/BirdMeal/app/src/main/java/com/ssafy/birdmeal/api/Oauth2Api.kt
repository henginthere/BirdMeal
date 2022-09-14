package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.response.OauthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Oauth2Api {

    // 로그인 요청
    @GET("user/login")
    suspend fun googleLogin(@Query("code") code: String): BaseResponse<OauthResponse>
}