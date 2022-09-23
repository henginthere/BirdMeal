package com.ssafy.birdmeal.view.market.shopping

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dttmm.web3test.wrapper.Trade
import com.ssafy.birdmeal.di.ApplicationClass.Companion.elenaContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.fundingContract
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.repository.CartRepository
import com.ssafy.birdmeal.utils.CA_FUNDING
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.fromEtherToWei
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel() {

    private val _productList: MutableStateFlow<List<CartEntity>>
        = MutableStateFlow(listOf())
    val productList get() = _productList

    private val _txList: MutableList<String> = mutableListOf()
    val txList get() = _txList

    private val _productCnt = MutableStateFlow(0)
    val productCnt get() = _productCnt

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice get() = _totalPrice

    private val _donationAmount = MutableStateFlow(0)
    val donationAmount get() = _donationAmount

    private val _totalAmount = MutableStateFlow(0)
    val totalAmount get() = _totalAmount

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

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

    // 장바구니 목록 조회
    fun getCartList() = viewModelScope.launch(Dispatchers.IO){
        cartRepository.getCartList().collectLatest {
            if(it is Result.Success){
                _productList.value = it.data
                _productCnt.value = it.data.size
                getTotalPrice()
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
        _totalPrice.value = price
        getTotalAmount()
    }

    // 총 결제금의 3% 기부금액을 합한 총 금액
    private fun getTotalAmount(){
        var amount = totalPrice.value!!.toDouble() * 0.03
        _donationAmount.value = floor(amount).toInt()

        var total = _totalPrice.value!! + _donationAmount.value!!
        _totalAmount.value = total
    }

    // 장바구니 결제하기
    fun buyingList(tradeContract : MutableList<Trade>){
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
        elenaContract.approve(CA_FUNDING, donationAmount.value.toLong().fromEtherToWei().toBigInteger()).sendAsync().get()
        fundingContract.funding(donationAmount.value.toBigInteger()).sendAsync().get()

        Log.d(TAG, "buyingList: 기부 완료 기부금 ${donationAmount.value}")
        // 서버에 주문내역 전송하기

        _successMsgEvent.postValue("주문이 완료되었습니다.")
    }

}