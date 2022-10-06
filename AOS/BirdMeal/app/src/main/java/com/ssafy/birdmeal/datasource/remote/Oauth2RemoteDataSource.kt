package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.Oauth2Api
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.request.JoinRequest
import com.ssafy.birdmeal.model.response.OauthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Oauth2RemoteDataSource @Inject constructor(
    private val oauth2Api: Oauth2Api
) {
    fun googleLogin(map: Map<String, String>): Flow<BaseResponse<OauthResponse>> = flow {
        emit(oauth2Api.googleLogin(map))
    }

    fun join(request: JoinRequest): Flow<BaseResponse<OauthResponse>> = flow {
        emit(oauth2Api.join(request))
    }

    fun checkCard(map: Map<String, String>): Flow<BaseResponse<Boolean>> = flow {
        emit(oauth2Api.checkCard(map))
    }
}