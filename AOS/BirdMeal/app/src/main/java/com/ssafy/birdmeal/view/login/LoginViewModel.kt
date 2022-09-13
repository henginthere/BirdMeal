package com.ssafy.birdmeal.view.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.repository.Oauth2Repository
import com.ssafy.birdmeal.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oauth2Repository: Oauth2Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email get() = _email.asStateFlow()

    private val _loginMsgEvent = SingleLiveEvent<String>()
    val loginMsgEvent get() = _loginMsgEvent

    private val _joinMsgEvent = SingleLiveEvent<String>()
    val joinMsgEvent get() = _joinMsgEvent

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    fun googleLogin(code: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            oauth2Repository.googleLogin(code).collectLatest {
//                Log.d(TAG, "googleLogin: $it")
//                if (it is Result.Success) {
//                    // 등록 되지 않은 사용자면 회원가입으로
//                    if (!it.data.success) {
//                        _email.value = email
//                        _joinMsgEvent.postValue("회원 가입 페이지로 이동 합니다.")
//                    }
//                    // 등록된 사용자면 홈 화면으로
//                    else {
//                        sharedPreferences.edit().putString(USER_SEQ, it.data.data?.userDto?.userSeq.toString())
//                            .apply()
//                        // 이미 등록된 사용자라서 토큰 바로 저장
//                        sharedPreferences.edit().putString(JWT, it.data.data?.token).apply()
//                        _loginMsgEvent.postValue("로그인 완료")
//                    }
//                } else if (it is Result.Error) {
//                    _errMsgEvent.postValue("서버 에러 발생")
//                }
//            }
        }
    }

}