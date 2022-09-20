package com.ssafy.birdmeal.view.market.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.repository.CartRepository
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel() {

    private val _productList: MutableStateFlow<List<CartEntity>>
        = MutableStateFlow(listOf())
    val productList get() = _productList.asStateFlow()

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
    fun getCartList(){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.getCartList().collectLatest {
                if(it is Result.Success){
                    _productList.value = it.data
                }
                else if(it is Result.Error){
                    _errMsgEvent.postValue("장바구니 목록 조회에 실패했습니다.")
                }
            }
        }
    }

}