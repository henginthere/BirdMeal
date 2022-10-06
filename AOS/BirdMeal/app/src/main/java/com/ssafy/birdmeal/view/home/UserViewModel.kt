package com.ssafy.birdmeal.view.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.birdmeal.di.ApplicationClass.Companion.elenaContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.exchangeContract
import com.ssafy.birdmeal.di.ApplicationClass.Companion.fundingContract
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.EOARequest
import com.ssafy.birdmeal.repository.UserRepository
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.fromWeiToEther
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import java.io.File
import java.security.Provider
import java.security.Security
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = SingleLiveEvent<UserDto>()
    val user get() = _user

    private val _contractErrMsgEvent = SingleLiveEvent<String>()
    val contractErrMsgEvent get() = _contractErrMsgEvent

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    private val _walletMsgEvent = SingleLiveEvent<Boolean>()
    val walletMsgEvent get() = _walletMsgEvent

    private val _tokenMsgEvent = SingleLiveEvent<String>()
    val tokenMsgEvent get() = _tokenMsgEvent

    private val _tokenChildMsgEvent = SingleLiveEvent<String>()
    val tokenChildMsgEvent get() = _tokenChildMsgEvent

    private val _tokenChildLoadingEvent = SingleLiveEvent<Boolean>()
    val tokenChildLoadingEvent get() = _tokenChildLoadingEvent

    private val _credentials = SingleLiveEvent<Credentials>()
    val credentials get() = _credentials

    private val _walletName = SingleLiveEvent<String>()
    val walletName get() = _walletName

    private val _walletAddress = SingleLiveEvent<String>()
    val walletAddress get() = _walletAddress

    private val _userInfoMsgEvent = SingleLiveEvent<String>()
    val userInfoMsgEvent get() = _userInfoMsgEvent

    private val _userUpdateMsgEvent = SingleLiveEvent<String>()
    val userUpdateMsgEvent get() = _userUpdateMsgEvent

    private val _userELN = SingleLiveEvent<Int>()
    val userELN get() = _userELN

    private val _userBalance = SingleLiveEvent<Long>()
    val userBalance get() = _userBalance


    // 지갑이 이미 있는지 확인
    fun checkPrivateKey(context: Context) {
        val path = context.getWalletPath()
        val walletFile = File(path)
        Log.d(TAG, "checkPrivateKey path: $path")

        if (!walletFile.exists()) {
            walletFile.mkdirs()
        }

        val files = walletFile.listFiles()

        // 지갑이 없는 경우
        if (files.isNullOrEmpty()) {
            setupBouncyCastle()
            _walletMsgEvent.postValue(false)
        }
        // 지갑이 이미 있는 경우
        else {
            val walletPath = files[0]
            val walletName = walletPath.toString().split("$path/")[1]

            setWalletName(walletName)

            val password = sharedPreferences.getString(WALLET_PASSWORD, "") ?: ""
            Log.d(TAG, "checkPrivateKey password: $password ")
            createCredentials(password)

            _walletMsgEvent.postValue(true)
        }
    }

    // keystore 파일 이름 저장
    fun setWalletName(walletName: String) {
        Log.d(TAG, "setWalletName walletName: $walletName")

        _walletName.value = walletName
    }

    // keystore를 이용해서 인증성 가져오기
    fun createCredentials(password: String) {
        val path = context.getWalletPath()

        try {
            val credentials = WalletUtils.loadCredentials(password, "$path/${walletName.value}")
            _credentials.value = credentials
            _walletAddress.value = credentials.address
            Log.d(TAG, "createCredentials address: ${credentials.address}")
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "createCredentials: $e")
            _errMsgEvent.postValue("인증서 가져오기 실패")
        }
    }

    // 유저 EOA 갱신
    fun updateUserEOA() = viewModelScope.launch(Dispatchers.IO) {

        val request = EOARequest(user.value!!.userSeq, credentials.value?.address!!)

        userRepository.updateUserEOA(request).collectLatest {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "updateUserEOA data: ${it.data}")
                    // 업데이트된 유저정보 받음
                    getUserInfo()
                    _userInfoMsgEvent.postValue("EOA 업데이트 성공")
                }
                is Result.Fail -> _errMsgEvent.postValue(it.data.msg)
                is Result.Error -> _errMsgEvent.postValue("서버 통신에 실패했습니다.")
            }
        }
    }

    // 유저 정보 요청
    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        Log.d(TAG, "getUserInfo userSeq: $userSeq")

        userRepository.getUserInfo(userSeq).collectLatest {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "getUserInfo data: ${it.data}")

                    _user.postValue(it.data.data)
                    _userInfoMsgEvent.postValue("유저 정보 가져오기 성공")
                }
                is Result.Fail -> _errMsgEvent.postValue(it.data.msg)
                is Result.Error -> _errMsgEvent.postValue("서버 통신에 실패했습니다.")
            }
        }
    }

    // 지갑 알고리즘 적용
    private fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: // Web3j will set up a provider  when it's used for the first time.
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            return
        }
        //There is a possibility  the bouncy castle registered by android may not have all ciphers
        //so we  substitute with the one bundled in the app.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    // 웹뷰에서 받아온 주소로 업데이트
    fun setAddress(data: String) {
        _user.value?.userAdd = data
    }

    // 회원정보 수정
    fun updateUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        val map = HashMap<String, String>()

        map.apply {
            put("userSeq", user.value?.userSeq.toString())
            put("userTel", user.value?.userTel ?: "")
            put("userAdd", user.value?.userAdd ?: "")
            put("userAddDetail", user.value?.userAddDetail ?: "")
            put("userNickname", user.value?.userNickname ?: "")
        }

        userRepository.updateUserProfile(map).collectLatest {
            when (it) {
                is Result.Success -> {
                    getUserInfo()
                    _userUpdateMsgEvent.postValue("회원정보 수정 성공")
                }
                is Result.Fail -> _errMsgEvent.postValue(it.data.msg)
                is Result.Error -> _errMsgEvent.postValue("서버 통신에 실패했습니다.")
            }
        }
    }

    // 유저 보유 토큰 값 조회 (컨트랙트)
    fun getUserTokenValue() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = elenaContract.balanceOf(user.value!!.userEoa).sendAsync().get()
            val value = result.fromWeiToEther().toInt()

            _userELN.postValue(value)
            _userBalance.postValue(result.fromWeiToEther().toLong())
            _successMsgEvent.postValue("유저 보유 토큰 불러오기 성공")
        } catch (e: Exception) {
            _contractErrMsgEvent.postValue(ERR_GET_USER_TOKEN)
            Log.d(TAG, "getUserTokenValue err: $e")
        }
    }

    // 유저 토큰 충전하기 (컨트랙트)
    fun fillUpToken(requestMoney: Int) = viewModelScope.launch(Dispatchers.IO) {

        try {
            val result = exchangeContract.changeMoney(requestMoney.toBigInteger()).sendAsync().get()
            Log.d(TAG, "fillUpToken: $result")
            // 유저 보유 토큰 재조회
            getUserTokenValue()

            _tokenMsgEvent.postValue(FILL_COMPLETED)
            _successMsgEvent.postValue("충전이 완료되었습니다.")
        } catch (e: Exception) {
            _tokenMsgEvent.postValue(FILL_ERR)
            _contractErrMsgEvent.postValue(ERR_FILLUP_TOKEN)
            Log.d(TAG, "fillUpToken err: $e")
        }
    }

    // 토큰 충전가능한지 검사 (결식 아동)
    fun checkFillUpTokenChild() = viewModelScope.launch(Dispatchers.IO) {
        // 충전 가능 상태인지 검사
        if (user.value?.userChargeState == false) {
            _tokenChildMsgEvent.postValue(FILL_ALREADY)
            return@launch
        }

        // 보유액이 10만 이상인 경우
        if (_userELN.value!! >= 100000) {
            _tokenChildMsgEvent.postValue(FILL_OVER)
            return@launch
        }

        _tokenChildMsgEvent.postValue(FILL_POSSIBLE)
    }

    // 아동 토큰 충전 시작 (컨트랙트)
    fun fillUpTokenChild() = viewModelScope.launch(Dispatchers.IO) {
        _tokenChildLoadingEvent.postValue(true)

        try {
            val result = fundingContract.takeMoney(true).sendAsync().get()
            Log.d(TAG, "fillUpTokenChild: $result")

            // 유저 토큰 값 다시 조회
            getUserTokenValue()

            // 아동 충전 완료 여부 변경
            fillUpCompleted()

            _tokenChildLoadingEvent.postValue(false)
            _tokenChildMsgEvent.postValue(FILL_COMPLETED)
        } catch (e: Exception) {
            _tokenChildMsgEvent.postValue(FILL_ERR)
            _contractErrMsgEvent.postValue(ERR_FILLUP_TOKEN_CHILD)
            Log.d(TAG, "fillUpTokenChild err: $e")
        }

    }

    // 아동 충전 완료 여부 변경
    fun fillUpCompleted() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.updateChildState(user.value?.userSeq!!).collectLatest {
            Log.d(TAG, "fillUpCompleted response: ${it}")
            when (it) {
                is Result.Success -> {
                    getUserInfo()
//                    _userUpdateMsgEvent.postValue("아동 충전 상태 변경 성공")
                }
                is Result.Fail -> _errMsgEvent.postValue(it.data.msg)
                is Result.Error -> _errMsgEvent.postValue("서버 통신에 실패했습니다.")
            }
        }
    }
}