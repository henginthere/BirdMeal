package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.model.response.OauthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Oauth2Api {

    @GET("login/oauth2/code/google")
    suspend fun googleLogin(@Query("code") code: String): OauthResponse
}