package com.ssafy.birdmeal.view.market.shopping

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.di.ApplicationClass.Companion.elenaContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.fundingContract
import com.ssafy.birdmeal.model.dto.OrderCompleteDto
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.model.request.OrderRequestDto
import com.ssafy.birdmeal.repository.CartRepository
import com.ssafy.birdmeal.repository.OrderRepository
import com.ssafy.birdmeal.utils.CA_FUNDING
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.fromEtherToWei
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.wrapper.Trade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
): ViewModel() {

    private val _productList: MutableStateFlow<List<CartEntity>>
        = MutableStateFlow(listOf())
    val productList get() = _productList

    private val _userRole = MutableStateFlow(false)

    private val _txList: MutableList<String> = mutableListOf()

    private val _orderList: MutableList<OrderRequestDto> = mutableListOf()

    private val _orderCompleteDto = SingleLiveEvent<OrderCompleteDto>()
    val orderCompleteDto get() = _orderCompleteDto

    private val _productCnt = MutableStateFlow(0)
    val productCnt get() = _productCnt

    var totalPrice = MutableStateFlow(0)

    private val _donationAmount = MutableStateFlow(0)
    val donationAmount get() = _donationAmount

    private val _totalAmount = MutableStateFlow(0)
    val totalAmount get() = _totalAmount

    private val _updateSuccessMsgEvent = SingleLiveEvent<String>()
    val updateSuccessMsgEvent get() = _updateSuccessMsgEvent

    private val _orderSuccessMsgEvent = SingleLiveEvent<String>()
    val orderSuccessMsgEvent get() = _orderSuccessMsgEvent

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    // 장바구니 품목 추가
    fun insert(cart: CartEntity){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.insertCart(cart)
        }
    }

    // 장바구니 품목 수정
    fun update(cart: CartEntity){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateCart(cart)
        }
    }

    // 장바구니 품목 삭제
    fun delete(cart: CartEntity){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCart(cart)
        }
    }

    // 장바구니 품목 삭제
    private fun clear(){
        _txList.clear() // 주문한 상품 해쉬 목록 삭제
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.clearCart()
        }
    }

    // 장바구니 목록 조회
    fun getCartList(userRole : Boolean) = viewModelScope.launch(Dispatchers.IO){
        _userRole.value = userRole

        cartRepository.getCartList().collectLatest {
            if(it is Result.Success){
                _productList.value = it.data
                _productCnt.value = it.data.size
                getTotalPrice()

                Log.d(TAG, "getCartList: 목록 조회 함 ${productCnt.value}")

                _updateSuccessMsgEvent.postValue("새 상품 목록을 불러왔습니다.")
            }
            else if(it is Result.Error){
                _errMsgEvent.postValue("장바구니 목록 조회에 실패했습니다.")
            }
        }
    }

    // 장바구니 물품의 전체 금액 더해주기
    private fun getTotalPrice(){
        var price = 0
        _productList.value.map { p ->
            var total = p.productPrice * p.productCount
            price += total
        }
        totalPrice.value = price
        getTotalAmount()
    }

    // 총 결제금의 3% 기부금액을 합한 총 금액
    private fun getTotalAmount(){
        if(!_userRole.value!!){ // 일반 유저 라면 기부 금액 계산
            var amount = totalPrice.value!!.toDouble() * 0.03
            _donationAmount.value = floor(amount).toInt()
        }


        var total = totalPrice.value!! + _donationAmount.value!!
        _totalAmount.value = total
    }

    // 장바구니 결제하기
    @RequiresApi(Build.VERSION_CODES.O)
    fun buyingList(tradeContract : MutableList<Trade>, userSeq : Int){
        // 상품 컨트랙트 마다 거래 처리하기
        productList.value.mapIndexed { idx, p ->
            // 상품 컨트랙트에 대한 엘레나 거래 승인
            Log.d(TAG, "buyingList: ${p.productName}, ${p.productCount}")
            var amount = (p.productPrice * p.productCount).toLong().fromEtherToWei()
            elenaContract.approve(p.productCa, amount.toBigInteger()).sendAsync().get()

            // 승인 이후 컨트랙트에 buying 함수 호출
            var result = tradeContract[idx].buying(p.productCount.toBigInteger()).sendAsync().get()
            tradeContract[idx].addOrderSheet(result.transactionHash, p.productCount.toBigInteger()).sendAsync().get()

            _txList.add(result.transactionHash)
        }
        // 기부 컨트랙트 엘레나 승인 및 3% 기부
        Log.d(TAG, "buyingList: 상품 컨트랙트 승인 - buying - addOrderSheet 완료")

        if(!_userRole.value){ // 일반유저면 간접기부도 하기
            elenaContract.approve(CA_FUNDING, donationAmount.value.toLong().fromEtherToWei().toBigInteger()).sendAsync().get()
            fundingContract.funding(_donationAmount.value.toBigInteger()).sendAsync().get()

            Log.d(TAG, "buyingList: 기부 완료 기부금 ${donationAmount.value}")
            createDonation() // DB에 기부 내역 저장하기
        }

        // 서버에 주문내역 전송하기
        productList.value.mapIndexed { idx, p ->
            var order = OrderRequestDto(userSeq, p.productCount, _txList[idx], p.productSeq)
            _orderList.add(order);
        }
        createOrderList(_orderList)
        // 주문 완료 객체 만들기
        createOrderComplete()
    }

    // 기부 내역 저장하기
    private fun createDonation() = viewModelScope.launch(Dispatchers.IO){
        
    }

    // 주문 내역 저장하기
    private fun createOrderList(orderRequestDtoList: List<OrderRequestDto>) =
        viewModelScope.launch(Dispatchers.IO){
            orderRepository.createOrderList(orderRequestDtoList).collectLatest {
                if(it is Result.Success){
                    _orderSuccessMsgEvent.postValue("주문이 완료되었습니다.")
                    clear() // 장바구니 초기화
                }
                if(it is Result.Fail){
                    _errMsgEvent.postValue(it.data.msg)
                }
                if(it is Result.Error){
                    _errMsgEvent.postValue("서버 통신에 실패했습니다.")
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createOrderComplete(){
        var date : String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        _orderCompleteDto.postValue(
            OrderCompleteDto(productList.value[0].productName, productList.value.size,
                null, date, totalAmount.value, _donationAmount.value)
        )
    }

}