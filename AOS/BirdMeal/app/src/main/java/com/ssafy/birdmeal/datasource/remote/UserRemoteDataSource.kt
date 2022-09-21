package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.UserApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.EOARequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    fun getUserInfo(userSeq: Int): Flow<BaseResponse<UserDto>> = flow {
        emit(userApi.getUserInfo(userSeq))
    }

    fun updateUserEOA(request: EOARequest): Flow<BaseResponse<String>> = flow {
        emit(userApi.updateUserEOA(request))
    }

    fun updateUserProfile(userSeq: Int, map: Map<String, String>): Flow<BaseResponse<String>> = flow {
        emit(userApi.updateUserProfile(userSeq, map))
    }
}