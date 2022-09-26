package com.ssafy.birdmeal.view.my_page

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.di.ApplicationClass.Companion.elenaContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.tradeContract
import com.ssafy.birdmeal.model.request.JoinRequest
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import com.ssafy.birdmeal.repository.OrderRepository
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.fromEtherToWei
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val orderRepository: OrderRepository

): ViewModel(){

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    private val _orderMsgEvent = SingleLiveEvent<String>()
    val orderMsgEvent get() = _orderMsgEvent

    private val _orderHistoryList:
            MutableStateFlow<Result<BaseResponse<List<OrderResponse>>>> =
        MutableStateFlow(Result.Uninitialized)
    val orderHistoryList get() = _orderHistoryList.asStateFlow()

    private val _orderDetailList:
            MutableStateFlow<Result<BaseResponse<List<OrderDetailResponse>>>> =
        MutableStateFlow(Result.Uninitialized)
    val orderDetailList get() = _orderDetailList.asStateFlow()

    // 내 주문내역 불러오기
    fun getMyOrderHistory() = viewModelScope.launch(Dispatchers.IO) {

        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        orderRepository.getMyOrderHistory(userSeq).collectLatest {
            Log.d(TAG, "getMyOrderHistory response: $it")

            if (it is Result.Success) {  // 불러오기 성공한 경우
                Log.d(TAG, "getMyOrderHistory data: ${it.data}")
                _orderHistoryList.value = it
                _orderMsgEvent.postValue("내 주문내역 불러오기 성공")
            }
            else if(it is Result.Fail){
                _errMsgEvent.postValue(it.data.msg)
            }
            else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 주문 상세 내역 불러오기
    fun getOrderDetail(orderSeq:Int) = viewModelScope.launch(Dispatchers.IO) {
        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        orderRepository.getOrderDetail(userSeq,orderSeq).collectLatest {
            Log.d(TAG, "getOrderDetail response: $it")

            if(it is Result.Success) {   // 불러오기 성공한 경우
                Log.d(TAG, "getOrderDetail data: ${it.data}")
                _orderDetailList.value = it
                _orderMsgEvent.postValue("주문 상세 내역 불러오기 성공")
            }
            else if(it is Result.Fail){
                _errMsgEvent.postValue(it.data.msg)
            }
            else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    //구매 확정
    fun updateOrderState(orderStateRequest: OrderStateRequest){
        viewModelScope.launch(Dispatchers.IO){
            orderRepository.updateOrderState(orderStateRequest).collectLatest {
                if(it is Result.Success) {
                    if(it.data.success){
//                        // 주문 컨트랙트 엘레나 승인 및 판매자에게 전송
//                        Log.d(TAG, "buyingList: 주문 컨트랙트 승인 - paying 완료")
//                        orderStateRequest.orderDetailSeq
//                        elenaContract.approve(CA_FUNDING, donationAmount.value.toLong().fromEtherToWei().toBigInteger()).sendAsync().get()
//                        tradeContract.paying(donationAmount.value.toBigInteger()).sendAsync().get()
                        _orderMsgEvent.postValue(it.data.msg)
                    }
                    else{
                        _orderMsgEvent.postValue(it.data.msg)
                    }
                }
                else if(it is Result.Error){
                    _errMsgEvent.postValue("서버 에러 발생")
                }
            }
        }

    }

}