package com.ssafy.birdmeal.view.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.repository.UserRepository
import com.ssafy.birdmeal.utils.Result
import com.ssafy.birdmeal.utils.SingleLiveEvent
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.USER_SEQ
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = SingleLiveEvent<UserDto>()
    val user get() = _user

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    // 유저 정보 요청
    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        /*
        테스트용 코드
         */
        _successMsgEvent.postValue("유저 정보 가져오기 성공")
        _user.postValue(UserDto(1, "", "", null, "", "", true, ""))

//        userRepository.getUserInfo(userSeq).collectLatest {
//            Log.d(TAG, "getUserInfo response: $it")
//
//            if (it is Result.Success) {
//                Log.d(TAG, "getUserInfo data: ${it.data}")
//
//                // 회원가입 성공한 경우
//                if (it.data.success) {
//                    _user.postValue(it.data.data)
//                    _successMsgEvent.postValue("유저 정보 가져오기 성공")
//                }
//            } else if (it is Result.Error) {
//                _errMsgEvent.postValue("서버 에러 발생")
//            }
//        }

    }
}