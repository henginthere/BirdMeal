package com.ssafy.birdmeal.view.market

import androidx.lifecycle.ViewModel
import com.ssafy.birdmeal.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

): ViewModel(){

    private val _errorMsgEvent = SingleLiveEvent<String>()
    val errorMsgEvent get() = _errorMsgEvent

}