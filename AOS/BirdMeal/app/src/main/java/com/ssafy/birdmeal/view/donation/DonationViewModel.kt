package com.ssafy.birdmeal.view.donation

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.di.ApplicationClass.Companion.elenaContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.fundingContract
import com.ssafy.birdmeal.model.dto.DonationHistoryDto
import com.ssafy.birdmeal.model.response.ChildHistoryResponse
import com.ssafy.birdmeal.repository.DonationRepository
import com.ssafy.birdmeal.repository.NftRepository
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.fromEtherToWei
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.fromWeiToEther
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.priceConvert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val donationRepository: DonationRepository,
    private val nftRepository: NftRepository
) : ViewModel() {

    private val _loadingMsgEvent = SingleLiveEvent<String>()
    val loadingMsgEvent get() = _loadingMsgEvent

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _donationMsgEvent = SingleLiveEvent<String>()
    val donationMsgEvent get() = _donationMsgEvent

    private val _donateMsgEvent = SingleLiveEvent<String>()
    val donateMsgEvent get() = _donateMsgEvent

    private val _donationPrice = SingleLiveEvent<String>()
    val donationPrice get() = _donationPrice

    private val _donationAmount = SingleLiveEvent<Long>()
    val donationAmount get() = _donationAmount

    private val _donationAllHistoryList = SingleLiveEvent<List<DonationHistoryDto>>()
    val donationAllHistoryList get() = _donationAllHistoryList

    private val _donationMyHistoryList = SingleLiveEvent<List<DonationHistoryDto>>()
    val donationMyHistoryList get() = _donationMyHistoryList

    @RequiresApi(Build.VERSION_CODES.O)
    val myDonationAmount =
        Transformations.map(_donationMyHistoryList) { list ->
            var sum = 0L;
            val today = LocalDate.now()
            Log.d(TAG, "${today.year}: ")
            Log.d(TAG, "${today.monthValue}: ")
            list.forEach {
                if (it.donationDate.substring(0, 4) == today.year.toString() &&
                    it.donationDate.substring(4, 6) == today.monthValue.toString()
                ) {
                    sum += it.donationPrice
                }
            }
            sum.priceConvert()
        }

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

    // 기부 가능한지 확인
    fun checkDonate(userBalance: Long, donationType: Boolean) {
        Log.d(TAG, "목표 기부액: ${donationPrice.value}")
        Log.d(TAG, "나의 잔액: $userBalance")

        if (donationPrice.value.isNullOrBlank()) {
            _errMsgEvent.postValue("기부 금액을 입력해주세요")
            _loadingMsgEvent.postValue(DONATE_EMPTY)
            return
        }

        val amount = donationPrice.value?.replace(",", "")?.toLong()!!
        _donationAmount.postValue(amount)

        if (amount > userBalance) {
            _errMsgEvent.postValue("기부 금액이 보유 잔액보다 많습니다")
            _loadingMsgEvent.postValue(DONATE_BALANCE)
            return
        }

        doDonate(amount, donationType)
    }

    // 기부 완료 상태 발생
    fun setDonateCompleted() {
        _loadingMsgEvent.postValue(DONATE_COMPLETED)
    }

    // 기부하기 (컨트랙트)
    fun doDonate(amount: Long, donationType: Boolean) = viewModelScope.launch(IO) {
        _loadingMsgEvent.postValue(DONATE_POSSIBLE)

        elenaContract.approve(CA_FUNDING, amount.fromEtherToWei().toBigInteger()).sendAsync().get()
        val result = fundingContract.funding(BigInteger.valueOf(amount)).sendAsync().get()
        Log.d(TAG, "fundingContract.funding: $result")

        insertDonationHistory(amount, donationType)

        _loadingMsgEvent.postValue(DONATE_COMPLETED)
    }

    // chip 선택시 기부 금액 증가
    fun plusDonationPrice(num: Int) {
        var current: Long? = null
        if (!_donationPrice.value.isNullOrBlank()) {
            current = _donationPrice.value?.replace(",", "")?.toLong()
        }
        val amount = (20000L * num - 10000L)
        if (current == null) {
            _donationPrice.postValue(amount.toString())
        } else {
            current = current?.plus(amount)
            _donationPrice.postValue(current.toString())
        }
    }

    // 전체 기부내역 불러오기
    fun getAllDonationHistory() = viewModelScope.launch(IO) {
        donationRepository.getAllDonationHistory().collectLatest {
            Log.d(TAG, "getAllDonationHistory response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getAllDonationHistory data: ${it.data}")

                // 불러오기 성공한 경우
                if (it.data.success) {
                    _donationAllHistoryList.postValue(it.data.data)
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
                    _donationMyHistoryList.postValue(it.data.data)
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