package com.ssafy.birdmeal.view.home

import android.content.*
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCreateWalletBinding
import com.ssafy.birdmeal.utils.*
import dagger.hilt.android.AndroidEntryPoint
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import java.io.File
import java.math.BigInteger
import javax.inject.Inject

@AndroidEntryPoint
class CreateWalletFragment :
    BaseFragment<FragmentCreateWalletBinding>(R.layout.fragment_create_wallet) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.state = WALLET_SELECT

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        // 지갑 생성하기
        btnCreate.setOnClickListener {
            tvToolbar.text = "새 지갑 생성"
            tvTitle.text = "지갑 생성을 위한\n비밀번호를 입력해주세요"
            ivTitle.setImageResource(R.drawable.ic_wallet)

            state = WALLET_CREATE
        }

        // 개인키 등록하기
        btnRegister.setOnClickListener {
            tvToolbar.text = "개인키 등록"
            tvTitle.text = "지갑 생성을 위해\n개인키와 비밀번호를 입력해주세요"
            ivTitle.setImageResource(R.drawable.ic_wallet)

            state = WALLET_PRIVATE
        }

        // 지갑 생성
        btnCreateWallet.setOnClickListener {
            if (validity(etPasswordCreate)) {
                val path = requireContext().getWalletPath()
                val walletFile = File(path)

                val password = etPasswordCreate.text.toString()

                try {
                    // generating the etherium wallet
                    val walletName = WalletUtils.generateLightNewWalletFile(password, walletFile)

                    setWalletInfo(walletName, password)

                } catch (e: java.lang.Exception) {
                    Log.e(TAG, "createWallet: $e")
                    showToast("지갑 생성 failed")
                }
            } else {
                showToast("비밀 번호를 입력해주세요")
            }
        }

        // 개인키 등록
        btnCreatePrivate.setOnClickListener {
            if (validity(etPrivateKey) && validity(etPasswordPrivate)) {
                val private = etPrivateKey.text.toString()
                val password = etPasswordPrivate.text.toString()
                val credentials = Credentials.create(private)
                val public = credentials.ecKeyPair.publicKey.toString(16)

                Log.d(TAG, "btnCreatePrivate private: ${BigInteger(private, 16)}")
                Log.d(TAG, "btnCreatePrivate public: $public")

                val path = TedPermissionProvider.context.getWalletPath()

                try {
                    val walletName = WalletUtils.generateWalletFile(
                        password,
                        ECKeyPair(BigInteger(private, 16), BigInteger(public, 16)),
                        File(path),
                        false
                    )

                    setWalletInfo(walletName, password)
                    userViewModel.setWalletName(walletName)
                    userViewModel.createCredentials(password)

                } catch (e: Exception) {
                    Log.d(TAG, "generateWalletFile: $e")
                    showToast("지갑 생성 실패")
                }
            } else {
                showToast("모두 입력해주세요")
            }
        }

        // privateKey 붙여넣기 버튼
        btnPastePrivate.setOnClickListener {
            getClipBoardData()
        }

        // privateKey 복사 버튼
        btnCopyPrivateKey.setOnClickListener {
            setClipBoard(tvPrivateKey.text.toString())
        }

        // EOA 복사 버튼
        btnCopyEoa.setOnClickListener {
            setClipBoard(tvEoa.text.toString())
        }

        // 돌아가기 버튼
        btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // 지갑 생성 후, path, credential 설정
    private fun setWalletInfo(walletName: String, password: String) {
        userViewModel.setWalletName(walletName)
        userViewModel.createCredentials(password)

        val privateKey =
            userViewModel.credentials.value?.ecKeyPair?.privateKey?.toString(16) ?: ""
        val eoa = userViewModel.credentials.value?.address.toString()

        sharedPreferences.edit().putString(WALLET_PASSWORD, password).apply()

        completedWallet(privateKey, eoa)

        showToast("지갑 생성이 완료되었습니다")
        hideKeyboard()
    }

    // 지갑 생성 완료시 호출
    private fun completedWallet(privateKey: String, eoa: String) = with(binding) {
        binding.state = WALLET_COMPLETE
        tvTitle.text = "생성된 지갑 정보입니다"
        tvPrivateKey.text = privateKey
        tvEoa.text = eoa
    }

    // 클립보드 복사하기
    private fun setClipBoard(data: String) {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("private_key", data)
        clipboard.setPrimaryClip(clip)
        Log.d(TAG, "setClipBoard: $data")
        showToast("복사가 완료되었습니다")
    }

    // 클립보드에서 첫번째 데이터 가져오기
    private fun getClipBoardData() {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var pasteData = ""

        if (clipboard.hasPrimaryClip() || clipboard.primaryClipDescription!!.hasMimeType(
                ClipDescription.MIMETYPE_TEXT_PLAIN
            )
        ) {
            val item = clipboard.primaryClip?.getItemAt(0)
            pasteData = item?.text.toString()

            binding.etPrivateKey.setText(pasteData)

            Log.d(TAG, "setClipBoard: $pasteData")
            showToast("붙여넣기가 완료되었습니다")
        }
    }

    // 키보드 내리기
    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}