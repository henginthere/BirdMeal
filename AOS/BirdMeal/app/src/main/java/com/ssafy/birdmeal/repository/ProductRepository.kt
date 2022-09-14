package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.ProductRemoteDataSource
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource
){

    fun getCategoryList() : Flow<Result<BaseResponse<List<CategoryDto>>>> = flow {
        emit(Result.Loading)
        productRemoteDataSource.getCategoryList().collect {
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

}