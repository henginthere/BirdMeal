package com.ssafy.birdmeal.view.donation.nft

import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.di.ApplicationClass.Companion.nftContract
import com.ssafy.birdmeal.model.dto.ChildPhotoCardDto
import com.ssafy.birdmeal.repository.NftRepository
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.USER_SEQ
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class NFTViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
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

    private val _mintingMsgEvent = SingleLiveEvent<String>()
    val mintingMsgEvent get() = _mintingMsgEvent

    private val _myNftList = SingleLiveEvent<List<String>>()
    val myNftList get() = _myNftList

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
                    // 이미지 정보 받아서 서버에 저장
                    insertPhotoCard(it.data.data)
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 서버에 포토카드 정보 업로드
    fun insertPhotoCard(imgUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        Log.d(TAG, "insertPhotoCard userSeq: $userSeq")

        val childPhotoCardDto = ChildPhotoCardDto(userSeq, imgUrl)

        nftRepository.insertPhotoCard(childPhotoCardDto).collectLatest {
            Log.d(TAG, "insertPhotoCard response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "insertPhotoCard data: ${it.data}")

                // 성공한 경우
                if (it.data.success) {
                    _fileMsgEvent.postValue("포토카드 제작 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 랜덤 포토카드 뽑기
    fun getPhotoCardUrl() = viewModelScope.launch(Dispatchers.IO) {
        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        Log.d(TAG, "getPhotoCardUrl userSeq: $userSeq")

        nftRepository.getPhotoCardUrl(userSeq).collectLatest {
            Log.d(TAG, "getPhotoCardUrl response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getPhotoCardUrl data: ${it.data}")

                // 성공한 경우
                if (it.data.success) {
                    // 받은 이미지 url로 민팅
                    doMinting(it.data.data.nftImg)
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 민팅하기 (컨트랙트)
    fun doMinting(imgUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = nftContract.mintNFT(imgUrl).sendAsync().get()
        Log.d(TAG, "doMinting: $result")

        _mintingMsgEvent.postValue(imgUrl)
    }

    // 나의 NFT 목록 불러오기 (컨트랙트)
    fun getMyNft() {
        val tokenIdList = nftContract.myToken.sendAsync().get()
        Log.d(TAG, "getMyNft: $tokenIdList")

        val myList = arrayListOf<String>()
        tokenIdList.forEach {
            val imgUrl = nftContract.tokenURI(it as BigInteger).sendAsync().get()
            myList.add(imgUrl)
        }
        _myNftList.postValue(myList)
    }
}