package com.ssafy.birdmeal.view.market

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.model.dto.ProductDto
import com.ssafy.birdmeal.repository.ProductRepository
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
class MarketViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel(){

    private val _categoryList : MutableStateFlow<Result<BaseResponse<List<CategoryDto>>>>
        = MutableStateFlow(Result.Uninitialized)
    val cateList get() = _categoryList.asStateFlow()

    private val _productList : MutableStateFlow<List<ProductDto>>
        = MutableStateFlow(listOf())
    val productList get() = _productList

    private val _product : MutableStateFlow<ProductDto>
        = MutableStateFlow(ProductDto(-1, -1, -1, "", "",0, "", "", "", false, "", ""))
    val product get() = _product.asStateFlow()

    private val _errorMsgEvent = SingleLiveEvent<String>()
    val errorMsgEvent get() = _errorMsgEvent

    private val _listSuccessEvent = SingleLiveEvent<String>()
    val listSuccessEvent get() = _listSuccessEvent

    private val _detailSuccessEvent = SingleLiveEvent<String>()
    val detailSuccessEvent get() = _detailSuccessEvent

    private val _searchSuccessEvent = SingleLiveEvent<String>()
    val searchSuccessEvent get() = _searchSuccessEvent

    // 카테고리 목록 조회
    fun getCategoryList(){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getCategoryList().collectLatest {
                if(it is Result.Success){ // 값을 제대로 받아옴
                     _categoryList.value = it
                }
                else if(it is Result.Fail){ // 값을 제대로 받아오지 못함
                    _errorMsgEvent.postValue(it.data.msg)
                }
                else if(it is Result.Error){ // 서버 통신 자체 오류
                    _errorMsgEvent.postValue("카테고리 목록 조회 중 통신에 실패했습니다.")
                }
            }
        }
    }

    // 카테고리 내 상품 목록 조회
    fun getProductList(categorySeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductList(categorySeq).collectLatest {
                if(it is Result.Success){ // 값을 제대로 받아옴
                    _productList.value = it.data.data
                    _listSuccessEvent.postValue("상품 목록 조회에 성공했습니다.")
                }
                else if(it is Result.Fail){ // 값을 제대로 받아오지 못함
                    _errorMsgEvent.postValue(it.data.msg)
                }
                else if(it is Result.Error){ // 서버 통신 자체 오류
                    _errorMsgEvent.postValue("상품 목록 조회 중 통신에 실패했습니다.")
                }
            }
        }
    }

    // 상품 상세정보 조회
    fun getProduct(productSeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProduct(productSeq).collectLatest {
                if(it is Result.Success){
                    _product.value = it.data.data
                    _detailSuccessEvent.postValue(it.data.msg)
                }
                else if(it is Result.Fail){
                    _errorMsgEvent.postValue(it.data.msg)
                }
                else if(it is Result.Error){
                    _errorMsgEvent.postValue("상품 상세정보 조회 중 통신에 실패했습니다.")
                }
            }
        }
    }

    // 상품 검색
    fun searchProduct(name: String) = viewModelScope.launch(Dispatchers.IO) {
            productRepository.searchProduct(name).collectLatest {
                Log.d(TAG, "searchProduct: $it")

                when (it) {
                    is Result.Success -> {
                        _productList.value = it.data.data
                        _searchSuccessEvent.postValue(it.data.msg)
                    }
                    is Result.Fail -> _errorMsgEvent.postValue(it.data.msg)
                    is Result.Error -> _errorMsgEvent.postValue("상품 검색 중 통신에 실패했습니다.")
                }
            }
        }

}