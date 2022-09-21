package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.datasource.remote.DonationRemoteDataSource
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DonationRepository @Inject constructor(
    private val donationRemoteDataSource: DonationRemoteDataSource
) {

    fun insertDonationHistory(donationHistory: DonationHistoryDto): Flow<Result<BaseResponse<Boolean>>> =
        flow {
            emit(Result.Loading)
            donationRemoteDataSource.insertDonationHistory(donationHistory).collect {
                emit(Result.Success(it))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    fun getAllDonationHistory(): Flow<Result<BaseResponse<List<DonationHistoryDto>>>> = flow {
        emit(Result.Loading)
        donationRemoteDataSource.getAllDonationHistory().collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun getMyDonationHistory(userSeq: Int): Flow<Result<BaseResponse<List<DonationHistoryDto>>>> = flow {
        emit(Result.Loading)
        donationRemoteDataSource.getMyDonationHistory(userSeq).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}