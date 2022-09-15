package com.ssafy.birdmeal.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogCreateWalletBinding

class CreateWalletDialog : DialogFragment() {

    lateinit var binding: DialogCreateWalletBinding

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

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        // 지갑 생성
        btnCreate.setOnClickListener {

        }

        // 개인키 등록
        btnRegister.setOnClickListener {

        }
    }
}