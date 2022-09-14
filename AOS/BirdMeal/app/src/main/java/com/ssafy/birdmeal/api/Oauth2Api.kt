package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.model.response.OauthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Oauth2Api {

    @GET("user/login")
    suspend fun googleLogin(@Query("code") code: String): OauthResponse
}