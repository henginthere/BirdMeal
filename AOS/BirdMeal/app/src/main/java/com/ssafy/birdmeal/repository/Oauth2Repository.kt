package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.Oauth2RemoteDataSource
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.JoinRequest
import com.ssafy.birdmeal.model.response.OauthResponse
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Oauth2Repository @Inject constructor(
    private val oauth2RemoteDataSource: Oauth2RemoteDataSource
) {
    fun googleLogin(code: String): Flow<Result<BaseResponse<OauthResponse>>> = flow {
        emit(Result.Loading)
        oauth2RemoteDataSource.googleLogin(code).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun join(request: JoinRequest): Flow<Result<BaseResponse<Boolean>>> = flow {
        oauth2RemoteDataSource.join(request).collect {
            emit(Result.Success(it))
        }
    }

    fun checkCard(cardNumber: String): Flow<Result<BaseResponse<Boolean>>> = flow {
        oauth2RemoteDataSource.checkCard(cardNumber).collect {
            emit(Result.Success(it))
        }
    }

    fun getUserInfo(userSeq: Int): Flow<Result<BaseResponse<UserDto>>> = flow {
        oauth2RemoteDataSource.getUserInfo(userSeq).collect {
            emit(Result.Success(it))
        }
    }
}