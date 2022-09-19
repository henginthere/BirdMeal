package com.ssafy.birdmeal.view.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.model.dto.ProductDto
import com.ssafy.birdmeal.repository.ProductRepository
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
class MarketViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel(){

    private val _categoryList : MutableStateFlow<Result<BaseResponse<List<CategoryDto>>>>
        = MutableStateFlow(Result.Uninitialized)
    val cateList get() = _categoryList.asStateFlow()

//    private val _productList : MutableStateFlow<Result<BaseResponse<List<ProductDto>>>>
//        = MutableStateFlow(Result.Uninitialized)
//    val productList get() = _productList.asStateFlow()

    val productList : List<ProductDto>
        = listOf(
        ProductDto(1, 1, 1, "", 11, "", "", "", false, "", ""),
        ProductDto(2, 1, 2, "", 11, "", "", "", false, "", ""),
        ProductDto(3, 1, 3, "", 11, "", "", "", false, "", ""),
        ProductDto(4, 1, 4, "", 11, "", "", "", false, "", ""),
        ProductDto(5, 1, 5, "", 11, "", "", "", false, "", ""),
        ProductDto(6, 1, 6, "", 11, "", "", "", false, "", ""),
    )

    private val _product : MutableStateFlow<ProductDto>
        = MutableStateFlow(ProductDto(-1, -1, -1, "", 0, "", "", "", false, "", ""))
    val product get() = _product.asStateFlow()

    private val _errorMsgEvent = SingleLiveEvent<String>()
    val errorMsgEvent get() = _errorMsgEvent

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

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
                     // _productList.value = it
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
                    _successMsgEvent.postValue(it.data.msg)
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

}