package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.Oauth2RemoteDataSource
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
    fun googleLogin(map: Map<String, String>): Flow<Result<BaseResponse<OauthResponse>>> = flow {
        emit(Result.Loading)
        oauth2RemoteDataSource.googleLogin(map).collect {
            if(it.success){
                emit(Result.Success(it))
            }
            else if(!it.success){
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun join(request: JoinRequest): Flow<Result<BaseResponse<OauthResponse>>> = flow {
        emit(Result.Loading)
        oauth2RemoteDataSource.join(request).collect {
            if(it.success){
                emit(Result.Success(it))
            }
            else if(!it.success){
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun checkCard(map: Map<String, String>): Flow<Result<BaseResponse<Boolean>>> = flow {
        emit(Result.Loading)
        oauth2RemoteDataSource.checkCard(map).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}