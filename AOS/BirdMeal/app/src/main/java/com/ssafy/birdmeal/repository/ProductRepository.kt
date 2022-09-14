package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.ProductRemoteDataSource
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.model.dto.ProductDto
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
    // 상품 카테고리 목록 조회
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

    // 카테고리 내 상품 목록 전체 조회
    fun getProductList(categorySeq: Int) : Flow<Result<BaseResponse<List<ProductDto>>>> = flow<Result<BaseResponse<List<ProductDto>>>> {
        emit(Result.Loading)
        productRemoteDataSource.getProductList(categorySeq).collect { 
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