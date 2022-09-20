package com.ssafy.birdmeal.view.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.birdmeal.model.dto.UserDto
import com.ssafy.birdmeal.model.request.EOARequest
import com.ssafy.birdmeal.repository.UserRepository
import com.ssafy.birdmeal.utils.*
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

    private val _errMsgEvent = SingleLiveEvent<String>()
    val errMsgEvent get() = _errMsgEvent

    private val _walletMsgEvent = SingleLiveEvent<Boolean>()
    val walletMsgEvent get() = _walletMsgEvent

    private val _credentials = SingleLiveEvent<Credentials>()
    val credentials get() = _credentials

    private val _walletName = SingleLiveEvent<String>()
    val walletName get() = _walletName

    private val _walletAddress = SingleLiveEvent<String>()
    val walletAddress get() = _walletAddress

    private val _userInfoMsgEvent = SingleLiveEvent<String>()
    val userInfoMsgEvent get() = _userInfoMsgEvent

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

            updateUserEOA(credentials.address)

        } catch (e: java.lang.Exception) {
            Log.e(TAG, "createCredentials: $e")
            _errMsgEvent.postValue("인증서 가져오기 실패")
        }
    }

    // 유저 EOA 갱신
    fun updateUserEOA(eoa: String) = viewModelScope.launch(Dispatchers.IO) {

        val request = EOARequest(user.value!!.userSeq, eoa)

        userRepository.updateUserEOA(request).collectLatest {
            Log.d(TAG, "updateUserEOA response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "updateUserEOA data: ${it.data}")

                // 회원가입 성공한 경우
                if (it.data.success) {
                    _userInfoMsgEvent.postValue("EOA 업데이트 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
            }
        }
    }

    // 유저 정보 요청
    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val userSeq = sharedPreferences.getInt(USER_SEQ, -1)
        Log.d(TAG, "getUserInfo userSeq: $userSeq")

        userRepository.getUserInfo(userSeq).collectLatest {
            Log.d(TAG, "getUserInfo response: $it")

            if (it is Result.Success) {
                Log.d(TAG, "getUserInfo data: ${it.data}")

                // 회원가입 성공한 경우
                if (it.data.success) {
                    _user.postValue(it.data.data)
                    _userInfoMsgEvent.postValue("유저 정보 가져오기 성공")
                }
            } else if (it is Result.Error) {
                _errMsgEvent.postValue("서버 에러 발생")
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
}