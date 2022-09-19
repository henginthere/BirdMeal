package com.ssafy.birdmeal.view.market.product.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.model.dto.SellerDto
import com.ssafy.birdmeal.repository.SellerRepository
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(
    private val sellerRepository: SellerRepository
): ViewModel() {

    private val _seller = MutableStateFlow(
        SellerDto("","", "", "", null, "", 0, "", "")
    )
    val seller get() = _seller.asStateFlow()

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    // 판매자 정보 조회
    fun getSellerInfo(sellerSeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            sellerRepository.getSellerInfo(sellerSeq).collectLatest {
                if(it is Result.Success){
                    Log.d(TAG, "getSellerInfo: ${it.data.data}")
                    _seller.value = it.data.data
                    _successMsgEvent.postValue(it.data.msg)
                }
                else if(it is Result.Fail){
                    _errMsgEvent.postValue(it.data.msg)
                }
                else if(it is Result.Error){
                    _errMsgEvent.postValue("판매자 정보 조회 중 통신에 실패했습니다.")
                }
            }
        }
    }

}