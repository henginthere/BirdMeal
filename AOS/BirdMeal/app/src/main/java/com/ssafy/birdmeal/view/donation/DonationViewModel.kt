package com.ssafy.birdmeal.view.donation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.di.ApplicationClass.Companion.fundingContract
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.model.response.ChildHistoryResponse
import com.ssafy.birdmeal.repository.DonationRepository
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.DecimalConverter.fromWeiToEther
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.priceConvert
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.USER_SEQ
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val donationRepository: DonationRepository
) : ViewModel() {

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _donationMsgEvent = SingleLiveEvent<String>()
    val donationMsgEvent get() = _donationMsgEvent

    private val _donateMsgEvent = SingleLiveEvent<String>()
    val donateMsgEvent get() = _donateMsgEvent

    private val _donationAllHistoryList:
            MutableStateFlow<Result<BaseResponse<List<DonationHistoryDto>>>> =
        MutableStateFlow(Result.Uninitialized)
    val donationAllHistoryList get() = _donationAllHistoryList.asStateFlow()

    private val _donationMyHistoryList:
            MutableStateFlow<Result<BaseResponse<List<DonationHistoryDto>>>> =
        MutableStateFlow(Result.Uninitialized)
    val donationMyHistoryList get() = _donationMyHistoryList.asStateFlow()

    private val _orderChildHistoryList:
            MutableStateFlow<Result<BaseResponse<List<ChildHistoryResponse>>>> =
        MutableStateFlow(Result.Uninitialized)
    val orderChildHistoryList get() = _orderChildHistoryList.asStateFlow()

    // 총 기부액 불러오기 (컨트랙트)
    fun getDonationAmount() = viewModelScope.launch(IO) {

        val result = fundingContract.currentBalance().sendAsync().get()
        val text = result.fromWeiToEther().priceConvert() + " ELN"

        _donationMsgEvent.postValue(text)
        Log.d(TAG, "fundingContract.currentBalance: $result")
    }

    // 기부하기 (컨트랙트)
    fun doDonate(donationPrice: Long, donationType: Boolean) = viewModelScope.launch(IO) {

        val result = fundingContract.funding(BigInteger.valueOf(donationPrice)).sendAsync().get()
        Log.d(TAG, "fundingContract.funding: $result")

        insertDonationHistory(donationPrice, true)
    }

    // 전체 기부내역 불러오기
    fun getAllDonationHistory() = viewModelScope.launch(IO) {
        donationRepository.getAllDonationHistory().collectLatest {
            Log.d(TAG, "getAllDonationHistory response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getAllDonationHistory data: ${it.data}")

                // 불러오기 성공한 경우
                if (it.data.success) {
                    _donationAllHistoryList.value = it
                    _donateMsgEvent.postValue("전체 기부내역 불러오기 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 내 기부내역 불러오기
    fun getMyDonationHistory() = viewModelScope.launch(IO) {

        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        Log.d(TAG, "getMyDonationHistory userSeq: $userSeq")

        donationRepository.getMyDonationHistory(userSeq).collectLatest {
            Log.d(TAG, "getMyDonationHistory response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getMyDonationHistory data: ${it.data}")

                // 불러오기 성공한 경우
                if (it.data.success) {
                    _donationMyHistoryList.value = it
                    _donateMsgEvent.postValue("나의 기부내역 불러오기 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 내 기부내역 저장하기
    fun insertDonationHistory(donationPrice: Long, donationType: Boolean) =
        viewModelScope.launch(IO) {

            val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
            Log.d(TAG, "insertDonationHistory userSeq: $userSeq")

            val donationHistory = DonationHistoryDto(
                userSeq = userSeq,
                donationPrice = donationPrice,
                donationType = donationType
            )

            donationRepository.insertDonationHistory(donationHistory).collectLatest {
                Log.d(TAG, "insertDonationHistory response: $it")

                if (it is Result.Success) {
                    Log.d(TAG, "insertDonationHistory data: ${it.data}")

                    // 저장하기 성공한 경우
                    if (it.data.success) {
                        _donateMsgEvent.postValue("기부를 완료하였습니다")
                    }
                } else if (it is Result.Error) {
                    _errMsgEvent.postValue("서버 에러 발생")
                }
            }
        }

    // 아이들 기부금 사용 내역 불러오기
    fun getChildOrderHistory() = viewModelScope.launch(IO) {
        donationRepository.getChildOrderHistory().collectLatest {
            Log.d(TAG, "getChildOrderHistory response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getChildOrderHistory data: ${it.data}")

                // 불러오기 성공한 경우
                if (it.data.success) {
                    _orderChildHistoryList.value = it
                    _donateMsgEvent.postValue("아이들 기부금 사용 내역 불러오기 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }
}