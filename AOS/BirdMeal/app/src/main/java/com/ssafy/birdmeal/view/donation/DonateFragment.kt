package com.ssafy.birdmeal.view.donation

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonateBinding
import com.ssafy.birdmeal.utils.CustomTextWatcher
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.getDecimalFormat
import com.ssafy.birdmeal.view.home.LoadingDialog
import com.ssafy.birdmeal.view.home.UserViewModel
import java.text.DecimalFormat

class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()
    private val loadingDialog by lazy { LoadingDialog("기부중...") }

    override fun init() {
        binding.apply {
            userVM = userViewModel
            donationVM = donationViewModel
            etAmount.apply {
                addTextChangedListener(CustomTextWatcher(this))
            }
        }

        initViewModelCallBack()
        userViewModel.getUserTokenValue()
        Log.d(TAG, "init: ${userViewModel.userBalance.value}")
        initClickListener()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            userBalance.observe(viewLifecycleOwner) {
                binding.tvBeforeDonate.text = getDecimalFormat(it) + "  ELN"
            }

            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }

        donationViewModel.apply {

            loadingMsgEvent.observe(viewLifecycleOwner) {
                Log.d(TAG, "loadingMsgEvent: $it")
                // 로딩 시작
                if (it) {
                    loadingDialog.show(childFragmentManager, "loadingDialog")
                } else {
                    loadingDialog.dismiss()
                }
            }

            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }

            // 기부 완료
            donateMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)

                // 나의 잔액 다시 불러옴
                userViewModel.getUserTokenValue()

                // EditText 초기화
                binding.etAmount.text = null
            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 기부하기
        btnDonte.setOnClickListener {
            val userBalance = userViewModel.userBalance.value ?: 0
            donationViewModel.doDonate(userBalance, true)

            etAmount.clearFocus()
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
//
//    private fun getDecimalFormat(number: Long): String {
//        val decimalFormat = DecimalFormat("#,###")
//        return decimalFormat.format(number)
//    }

}