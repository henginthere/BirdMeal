package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.UserRemoteDataSource
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.EOARequest
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) {

    fun getUserInfo(userSeq: Int): Flow<Result<BaseResponse<UserDto>>> = flow {
        emit(Result.Loading)
        userRemoteDataSource.getUserInfo(userSeq).collect {
            if(it.success){
                emit(Result.Success(it))
            } else {
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun updateUserEOA(request: EOARequest): Flow<Result<BaseResponse<String>>> = flow {
        emit(Result.Loading)
        userRemoteDataSource.updateUserEOA(request).collect {
            if(it.success){
                emit(Result.Success(it))
            } else {
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun updateUserProfile(map: Map<String, String>): Flow<Result<BaseResponse<String>>> = flow {
        emit(Result.Loading)
        userRemoteDataSource.updateUserProfile(map).collect {
            if(it.success){
                emit(Result.Success(it))
            } else {
                emit(Result.Fail(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}