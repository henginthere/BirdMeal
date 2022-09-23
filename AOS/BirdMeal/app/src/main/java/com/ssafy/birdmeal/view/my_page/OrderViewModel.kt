package com.ssafy.birdmeal.view.my_page

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.response.OrderResponse
import com.ssafy.birdmeal.repository.OrderRepository
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.USER_SEQ
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

    private val _orderMsgEvent = SingleLiveEvent<String>()
    val orderMsgEvent get() = _orderMsgEvent

    private val _orderHistoryList:
            MutableStateFlow<Result<BaseResponse<List<OrderResponse>>>> =
        MutableStateFlow(Result.Uninitialized)
    val orderHistoryList get() = _orderHistoryList.asStateFlow()

    // 내 주문내역 불러오기
    fun getMyOrderHistory() = viewModelScope.launch(Dispatchers.IO) {

        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        orderRepository.getMyOrderHistory(userSeq).collectLatest {
            Log.d(TAG, "getMyOrderHistory response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getMyOrderHistory data: ${it.data}")

                // 불러오기 성공한 경우
                if (it.data.success) {
                    _orderHistoryList.value = it
                    _orderMsgEvent.postValue("내 주문내역 불러오기 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }
}