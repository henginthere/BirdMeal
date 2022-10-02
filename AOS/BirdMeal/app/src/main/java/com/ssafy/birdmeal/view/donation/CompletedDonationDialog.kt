package com.ssafy.birdmeal.view.donation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogCompletedDonationDialogBinding
import com.ssafy.birdmeal.utils.Converter.DecimalConverter.priceConvert
import com.ssafy.birdmeal.utils.getTodayInfo
import com.ssafy.birdmeal.view.home.UserViewModel

class CompletedDonationDialog : DialogFragment() {

    lateinit var binding: DialogCompletedDonationDialogBinding
    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_completed_donation_dialog,
            container,
            false
        )
        // 배경 투명하게 바꿔줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.amount = donationViewModel.donationAmount.value?.priceConvert() + " ELN"
        binding.date = getTodayInfo()

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        btnClose.setOnClickListener {
            dismiss()
        }
    }
}