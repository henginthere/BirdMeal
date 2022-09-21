package com.ssafy.birdmeal.view.donation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ssafy.birdmeal.repository.DonationRepository
import com.ssafy.birdmeal.utils.BOLCKCHAIN_NETWORK_CHAINID
import com.ssafy.birdmeal.utils.CA_FUNDING
import com.ssafy.birdmeal.utils.DecimalConverter.priceConvert
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.wrapper.Funding
import dagger.hilt.android.lifecycle.HiltViewModel
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.FastRawTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import org.web3j.utils.Convert
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val donationRepository: DonationRepository,
    private val sharedPreferences: SharedPreferences,
    private val gasProvider: StaticGasProvider,
    private val web3j: Web3j,
    private val pollingProcessor: PollingTransactionReceiptProcessor
) : ViewModel() {

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _donationMsgEvent = SingleLiveEvent<String>()
    val donationMsgEvent get() = _donationMsgEvent

    // 총 기부액 불러오기
    fun getDonationAmount(credentials: Credentials) {
        val manager = FastRawTransactionManager(
            web3j, credentials, BOLCKCHAIN_NETWORK_CHAINID,
            pollingProcessor
        )

        val funding = Funding.load(CA_FUNDING, web3j, manager, gasProvider)
        val result = funding.currentBalance().sendAsync().get()
        val text =
            Convert.fromWei(result.toBigDecimal(), Convert.Unit.ETHER).priceConvert() + " ELN"

        _donationMsgEvent.postValue(text)
        Log.d(TAG, "funding.currentBalance: $result")
    }
}