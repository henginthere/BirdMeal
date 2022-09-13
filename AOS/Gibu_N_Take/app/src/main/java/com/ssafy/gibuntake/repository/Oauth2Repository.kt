package com.ssafy.gibuntake.repository

import com.ssafy.gibuntake.datasource.remote.Oauth2RemoteDataSource
import com.ssafy.gibuntake.model.response.OauthResponse
import com.ssafy.gibuntake.utils.Result
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
    fun googleLogin(code : String): Flow<Result<OauthResponse>> = flow {
        emit(Result.Loading)
        oauth2RemoteDataSource.googleLogin(code).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}