package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.NftApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.ChildPhotoCardDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NftRemoteDataSource @Inject constructor(private val nftApi: NftApi) {
    fun insertPhotoCard(childNft: ChildPhotoCardDto): Flow<BaseResponse<Any>> = flow {
        emit(nftApi.insertPhotoCard(childNft))
    }

    fun getMyPhotoCard(userSeq: Int) : Flow<BaseResponse<List<ChildPhotoCardDto>?>> = flow {
        emit(nftApi.getMyPhotoCard(userSeq))
    }

    fun createNftFromPhotoCard(userSeq: Int) : Flow<BaseResponse<Any>> = flow {
        emit(nftApi.createNftFromPhotoCard(userSeq))
    }

    fun getPhotoCardUrl(userSeq: Int) : Flow<BaseResponse<Any>> = flow {
        emit(nftApi.getPhotoCardUrl(userSeq))
    }
}