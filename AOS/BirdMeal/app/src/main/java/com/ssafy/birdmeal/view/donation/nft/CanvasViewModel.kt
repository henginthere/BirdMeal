package com.ssafy.birdmeal.view.donation.nft

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.repository.NftRepository
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CanvasViewModel @Inject constructor(
    private val nftRepository: NftRepository
) : ViewModel() {

    private val _color = MutableLiveData(Color.parseColor("#000000"))
    val color get() = _color

    val text = MutableLiveData<String>()

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _textMsgEvent = SingleLiveEvent<Boolean>()
    val textMsgEvent get() = _textMsgEvent

    private val _fileMsgEvent = SingleLiveEvent<String>()
    val fileMsgEvent get() = _fileMsgEvent

    fun setColor(color: Int) {
        _color.value = color
    }

    fun emitTextMsg() {
        _textMsgEvent.postValue(true)
    }

    // 포토카드 이미지서버에 업로드
    fun saveFile(img: MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "saveFile: ${img.body}")
        nftRepository.saveFile(img).collectLatest {
            Log.d(TAG, "saveFile response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "saveFile data: ${it.data}")

                // 성공한 경우
                if (it.data.success) {
                    _fileMsgEvent.postValue("사진 업로드 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }
}