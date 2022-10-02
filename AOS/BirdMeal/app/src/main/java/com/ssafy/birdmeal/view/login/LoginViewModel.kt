package com.ssafy.birdmeal.view.login

import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.birdmeal.model.request.JoinRequest
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

    private val _joinSuccessMsgEvent = SingleLiveEvent<String>()
    val joinSuccessMsgEvent get() = _joinSuccessMsgEvent

    private val _ocrMsgEvent = SingleLiveEvent<String>()
    val ocrMsgEvent get() = _ocrMsgEvent

    val cardNumber = MutableStateFlow("")

    private val _childSuccessMsgEvent = SingleLiveEvent<String>()
    val childSuccessMsgEvent get() = _childSuccessMsgEvent

    private val _childFailMsgEvent = SingleLiveEvent<String>()
    val childFailMsgEvent get() = _childFailMsgEvent

    fun googleLogin(code: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val map = HashMap<String, String>();
            map.put("googleAccessToken", code)

            oauth2Repository.googleLogin(map).collectLatest {
                Log.d(TAG, "googleLogin response: $it")
                if (it is Result.Success) {
                    Log.d(TAG, "googleLogin data: ${it.data}")
                    // 등록된 사용자면 홈 화면으로
                    sharedPreferences.edit().putInt(USER_SEQ, it.data.data?.userSeq)
                        .apply()
                    // 이미 등록된 사용자라서 토큰 바로 저장
                    sharedPreferences.edit().putString(JWT, it.data.data?.tokenDto.accessToken)
                        .apply()
                    _loginMsgEvent.postValue("로그인 완료")
                } else if (it is Result.Error) {
                    _errMsgEvent.postValue("서버 에러 발생")
                }
                // 등록 되지 않은 사용자면 회원가입으로
                else if (it is Result.Fail) {
                    _email.value = email
                    _joinMsgEvent.postValue("회원 가입 페이지로 이동 합니다.")
                }
            }
        }
    }

    // 회원가입 요청
    fun join(userRole: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val nickname = email.value.split("@")[0]
        val request = JoinRequest(email.value, nickname, userRole)

        oauth2Repository.join(request).collectLatest {
            Log.d(TAG, "join response: $it")
            if (it is Result.Success) {
                Log.d(TAG, "join data: ${it.data}")

                // 회원가입 성공한 경우
                sharedPreferences.edit().putInt(USER_SEQ, it.data.data?.userSeq)
                    .apply()
                sharedPreferences.edit().putString(JWT, it.data.data?.tokenDto.accessToken)
                    .apply()
                _joinSuccessMsgEvent.postValue("회원 가입 성공")

            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            } else if (it is Result.Fail) {
                _errMsgEvent.postValue(it.data.msg)
            }
        }
    }

    // 결식카드 맞는지 확인
    fun checkCard() = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "checkCard: ${cardNumber.asStateFlow().value}")
        // 유효성 검사
        if (TextUtils.isEmpty(cardNumber.asStateFlow().value)) {

            _errMsgEvent.postValue("카드 번호를 인식해주세요")
            return@launch
        }

        val map = HashMap<String, String>()
        map.put("childCardNum", cardNumber.value!!)
        map.put("userEmail", _email.value)

        oauth2Repository.checkCard(map).collectLatest {
            Log.d(TAG, "join response: $it")
            if (it is Result.Success) {
                Log.d(TAG, "join data: ${it.data}")

                // 카드 인증 성공
                if (it.data.success) {
                    _childSuccessMsgEvent.postValue("결식 아동 인증 성공")
                }
                // 카드 인증 실패
                else {
                    _childFailMsgEvent.postValue("결식 아동 인증 실패")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // OCR 인식한 결과
    fun ocr(text: String) {
        cardNumber.value = text.replace(" ", "")
        _ocrMsgEvent.postValue("OCR 인식 성공")
    }
}