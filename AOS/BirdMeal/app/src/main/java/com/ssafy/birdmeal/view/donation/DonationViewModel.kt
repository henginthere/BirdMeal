package com.ssafy.birdmeal.view.donation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.di.ApplicationClass.Companion.fundingContract
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.DecimalConverter.fromWeiToEther
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.priceConvert
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _donationMsgEvent = SingleLiveEvent<String>()
    val donationMsgEvent get() = _donationMsgEvent

    // 총 기부액 불러오기 (컨트랙트)
    fun getDonationAmount() = viewModelScope.launch(IO) {

        val result = fundingContract.currentBalance().sendAsync().get()
        val text = result.fromWeiToEther().priceConvert() + " ELN"

        _donationMsgEvent.postValue(text)
        Log.d(TAG, "funding.currentBalance: $result")
    }
}