package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.NftRemoteDataSource
import com.ssafy.birdmeal.model.dto.ChildPhotoCardDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NftRepository @Inject constructor(private val nftRemoteDataSource: NftRemoteDataSource){
    fun insertPhotoCard(childNft: ChildPhotoCardDto): Flow<Result<BaseResponse<Any>>> = flow {
        emit(Result.Loading)
        nftRemoteDataSource.insertPhotoCard(childNft).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun getMyPhotoCard(userSeq: Int) : Flow<Result<BaseResponse<List<ChildPhotoCardDto>?>>> = flow {
        emit(Result.Loading)
        nftRemoteDataSource.getMyPhotoCard(userSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e->
        emit(Result.Error(e))
    }

    fun createNftFromPhotoCard(userSeq: Int) : Flow<Result<BaseResponse<Any>>> = flow {
        emit(Result.Loading)
        nftRemoteDataSource.createNftFromPhotoCard(userSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e->
        emit(Result.Error(e))
    }

    fun getPhotoCardUrl(userSeq: Int) : Flow<Result<BaseResponse<Any>>> = flow {
        emit(Result.Loading)
        nftRemoteDataSource.getPhotoCardUrl(userSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e->
        emit(Result.Error(e))
    }
}