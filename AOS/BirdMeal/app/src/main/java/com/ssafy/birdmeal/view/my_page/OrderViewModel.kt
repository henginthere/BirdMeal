package com.ssafy.birdmeal.view.my_page

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.di.ApplicationClass.Companion.elenaContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.tradeContract
import com.ssafy.birdmeal.model.request.OrderStateRequest
import com.ssafy.birdmeal.model.response.OrderDetailResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import com.ssafy.birdmeal.model.response.OrderTHashResponse
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

) : ViewModel() {

    private val _orderDetailHash: MutableStateFlow<OrderTHashResponse> =
        MutableStateFlow(OrderTHashResponse(-1, "", -1, -1, ""))
    val orderDetailHash get() = _orderDetailHash.asStateFlow()

    private val _contractErrMsgEvent = SingleLiveEvent<String>()
    val contractErrMsgEvent get() = _contractErrMsgEvent

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _orderDetailSeqMsgEvent = SingleLiveEvent<Int>()
    val orderDetailSeqMsgEvent get() = _orderDetailSeqMsgEvent

    private val _orderMsgEvent = SingleLiveEvent<String>()
    val orderMsgEvent get() = _orderMsgEvent

    private val _loadingAssumeMsgEvent = SingleLiveEvent<String>()
    val loadingAssumeMsgEvent get() = _loadingAssumeMsgEvent

    private val _orderSeq = SingleLiveEvent<Int>()

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
            } else if (it is Result.Fail) {
                _errMsgEvent.postValue(it.data.msg)
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 주문 상세 내역 불러오기
    fun getOrderDetail(orderSeq: Int) = viewModelScope.launch(Dispatchers.IO) {
        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        _orderSeq.postValue(orderSeq)

        orderRepository.getOrderDetail(userSeq, orderSeq).collectLatest {
            Log.d(TAG, "getOrderDetail response: $it")

            if (it is Result.Success) {   // 불러오기 성공한 경우
                Log.d(TAG, "getOrderDetail data: ${it.data}")
                _orderDetailList.value = it
                _orderMsgEvent.postValue("주문 상세 내역 불러오기 성공")
            } else if (it is Result.Fail) {
                _errMsgEvent.postValue(it.data.msg)
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    //구매 확정
    fun updateOrderState(orderStateRequest: OrderStateRequest) {

        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.updateOrderState(orderStateRequest).collectLatest {
                if (it is Result.Success) {
                    if (it.data.success) {

                        // 주문 컨트랙트 엘레나 승인 및 판매자에게 전송
                        Log.d(TAG, "buyingList: 주문 컨트랙트 승인 - paying 완료")
                        try {
                            elenaContract.approve(
                                orderDetailHash.value.productCa,
                                (orderDetailHash.value.productPrice * orderDetailHash.value.orderQuantity).toLong()
                                    .fromEtherToWei().toBigInteger()
                            ).sendAsync().get()
                            tradeContract.paying(orderDetailHash.value.orderTHash).sendAsync().get()
                            _orderMsgEvent.postValue(it.data.msg)

                            getOrderDetail(_orderSeq.value!!)
                        } catch (e: Exception) {
                            _contractErrMsgEvent.postValue(ERR_UPDATE_ORDER_STATE)
                            Log.d(TAG, "updateOrderState err: $e")
                        } finally {
                            _loadingAssumeMsgEvent.postValue("로딩 종료")
                        }

                    } else {
                        _orderMsgEvent.postValue(it.data.msg)
                    }
                } else if (it is Result.Error) {
                    _errMsgEvent.postValue("서버 에러 발생")
                }
            }
        }

    }

    //해시 및 주문 상세 정보 조회
    fun getOrderTHash(orderDetailSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getOrderTHash(orderDetailSeq).collectLatest {
                if (it is Result.Success) {
                    _orderDetailHash.value = it.data.data
                    _orderDetailSeqMsgEvent.postValue(orderDetailSeq)
                    Log.d(TAG, "getOrderTHash: ${_orderDetailHash.value}")
                }
            }
        }
    }

    // 컨트랙트 오류 메시지 발생
    fun setContractErr(msg: String) {
        _contractErrMsgEvent.postValue(msg)
    }
}