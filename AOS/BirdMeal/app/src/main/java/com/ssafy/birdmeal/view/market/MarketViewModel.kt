package com.ssafy.birdmeal.view.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.CategoryDto
import com.ssafy.birdmeal.repository.ProductRepository
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.Result
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

//    private val _categoryList : MutableStateFlow<Result<BaseResponse<List<CategoryDto>>>>
//        = MutableStateFlow(Result.Uninitialized)
//    val categoryList get() = _categoryList.asStateFlow()

    val categoryList : List<CategoryDto>
        = listOf(CategoryDto(1, "육류", "a"),
            CategoryDto(2, "양식", "b"),
            CategoryDto(3, "중식", "c"),
            CategoryDto(4, "분식", "d"),
            CategoryDto(5, "한식", "e"),
            CategoryDto(6, "햄버거", "f"),
        )

    private val _errorMsgEvent = SingleLiveEvent<String>()
    val errorMsgEvent get() = _errorMsgEvent

    // 카테고리 목록 조회
    fun getCategoryList(){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getCategoryList().collectLatest {
                if(it is Result.Success){ // 값을 제대로 받아옴
                    // _categoryList.value = it
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

    // 카테고리 내 상품 목록 조회
    fun getProductList(categorySeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductList(categorySeq).collectLatest {
                if(it is Result.Success){ // 값을 제대로 받아옴
                    // _categoryList.value = it
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

}