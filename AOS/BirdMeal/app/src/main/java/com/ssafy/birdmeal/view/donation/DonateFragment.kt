package com.ssafy.birdmeal.view.donation

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonateBinding
import com.ssafy.birdmeal.utils.CustomTextWatcher
import com.ssafy.birdmeal.view.home.UserViewModel

class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {
        binding.userVM = userViewModel
        binding.donationVM = donationViewModel
        userViewModel.getUserTokenValue()

        binding.etAmount.apply {
            addTextChangedListener(CustomTextWatcher(this))
        }

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }

        donationViewModel.apply {
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }

            // 기부 완료
            donateMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
                // 전체 기부금 다시 불러옴
                donationViewModel.getDonationAmount()

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

}