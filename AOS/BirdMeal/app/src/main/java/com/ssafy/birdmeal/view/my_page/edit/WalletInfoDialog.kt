package com.ssafy.birdmeal.view.my_page.edit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogWalletInfoBinding
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.home.UserViewModel

class WalletInfoDialog : DialogFragment() {

    lateinit var binding: DialogWalletInfoBinding
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_wallet_info, container, false)
        // 배경 투명하게 바꿔줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getKey()

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        // privateKey 복사 버튼
        btnCopyPrivateKey.setOnClickListener {
            setClipBoard(tvPrivateKey.text.toString())
        }

        // EOA 복사 버튼
        btnCopyEoa.setOnClickListener {
            setClipBoard(tvEoa.text.toString())
        }

        btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun getKey() {
        val privateKey =
            userViewModel.credentials.value?.ecKeyPair?.privateKey?.toString(16) ?: ""
        val eoa = userViewModel.credentials.value?.address.toString()
        completedWallet(privateKey, eoa)
    }

    private fun completedWallet(privateKey: String, eoa: String) = with(binding) {
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

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}