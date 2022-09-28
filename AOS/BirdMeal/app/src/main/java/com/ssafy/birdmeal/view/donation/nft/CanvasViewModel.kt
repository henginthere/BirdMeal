package com.ssafy.birdmeal.view.donation.nft

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CanvasViewModel : ViewModel() {

    private val _color = MutableLiveData(Color.parseColor("#000000"))
    val color get() = _color

    fun setColor(color: Int) {
        _color.value = color
    }
}