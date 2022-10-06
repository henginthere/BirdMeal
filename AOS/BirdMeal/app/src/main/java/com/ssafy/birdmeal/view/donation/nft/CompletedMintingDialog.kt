package com.ssafy.birdmeal.view.donation.nft

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogCompletedMintingBinding

class CompletedMintingDialog(val imgUrl: String) : DialogFragment() {

    var binding: DialogCompletedMintingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_completed_minting, container, false)

        // 배경 투명하게 바꿔줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imgUrl = this.imgUrl

        initClickListener()
    }

    private fun initClickListener() = with(binding!!) {
        btnOk.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}