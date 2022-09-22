package com.ssafy.birdmeal.datasource.remote

import com.ssafy.birdmeal.api.DonationApi
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.model.response.ChildHistoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DonationRemoteDataSource @Inject constructor(
    private val donationApi: DonationApi
) {

    fun insertDonationHistory(donationHistory: DonationHistoryDto): Flow<BaseResponse<Boolean>> =
        flow {
            emit(donationApi.insertDonationHistory(donationHistory))
        }

    fun getAllDonationHistory(): Flow<BaseResponse<List<DonationHistoryDto>>> = flow {
        emit(donationApi.getAllDonationHistory())
    }

    fun getMyDonationHistory(userSeq: Int): Flow<BaseResponse<List<DonationHistoryDto>>> = flow {
        emit(donationApi.getMyDonationHistory(userSeq))
    }

    fun getChildOrderHistory(): Flow<BaseResponse<List<ChildHistoryResponse>>> = flow {
        emit(donationApi.getChildOrderHistory())
    }
}