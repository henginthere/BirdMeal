package com.ssafy.birdmeal.view.donation.nft

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.birdmeal.utils.SingleLiveEvent

class CanvasViewModel : ViewModel() {

    private val _color = MutableLiveData(Color.parseColor("#000000"))
    val color get() = _color

    val text = MutableLiveData<String>()

    private val _textMsgEvent = SingleLiveEvent<Boolean>()
    val textMsgEvent get() = _textMsgEvent

    fun setColor(color: Int) {
        _color.value = color
    }

    fun emitTextMsg() {
        _textMsgEvent.postValue(true)
    }
}