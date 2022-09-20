package com.ssafy.birdmeal.view.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogCreateWalletBinding
import com.ssafy.birdmeal.utils.*
import org.web3j.crypto.WalletUtils
import java.io.File

class CreateWalletDialog : DialogFragment() {

    lateinit var binding: DialogCreateWalletBinding
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_wallet, container, false)
        // 배경 투명하게 바꿔줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.state = WALLET_SELECT

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        // 지갑 생성하기
        btnCreate.setOnClickListener {
            tvTitle.text = "지갑 비밀번호를 입력해주세요"
            state = WALLET_CREATE
        }

        // 개인키 등록하기
        btnRegister.setOnClickListener {

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

                    userViewModel.setWalletName(walletName)
                    userViewModel.createCredentials(password)

                    val privateKey =
                        userViewModel.credentials.value?.ecKeyPair?.privateKey?.toString(16) ?: ""
                    val eoa = userViewModel.credentials.value?.address.toString()
                    completedWallet(privateKey, eoa)

                    showToast("지갑 생성이 완료되었습니다")

                    hideKeyboard()

                } catch (e: java.lang.Exception) {
                    Log.e(TAG, "createWallet: $e")
                    showToast("지갑 생성 failed")
                }
            } else {
                showToast("비밀 번호를 입력해주세요")
            }
        }

        // privateKey 복사 버튼
        btnCopyPrivateKey.setOnClickListener {
            setClipBoard(tvPrivateKey.text.toString())
        }

        // EOA 복사 버튼
        btnCopyEoa.setOnClickListener {
            setClipBoard(tvEoa.text.toString())
        }

        // 닫기 버튼
        btnClose.setOnClickListener {
            dismiss()
        }
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

    // 키보드 내리기
    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    // 유효성 검사
    private fun validity(et: EditText) = !TextUtils.isEmpty(et.text)

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}