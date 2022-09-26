package com.ssafy.birdmeal.view.donation

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : BaseFragment<FragmentDonationBinding>(R.layout.fragment_donation) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.donationVM = donationViewModel
        donationViewModel.getDonationAmount()
        userViewModel.getUserTokenValue()

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() = with(binding) {
        userViewModel.apply {
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }

        donationViewModel.apply {
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }

            // 불러온 전체 기부액 표시
            donationMsgEvent.observe(viewLifecycleOwner) {
                tvBalance.text = it
            }

            // 기부 완료
            donateMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
                // 전체 기부금 다시 불러옴
                donationViewModel.getDonationAmount()

                // 나의 잔액 다시 불러옴
                userViewModel.getUserTokenValue()
            }
        }
    }

    private fun initClickListener() = with(binding) {

        // 기부하기 페이지로 가기
        btnDonte.setOnClickListener {
            findNavController().navigate(R.id.action_donationFragment_to_donateFragment)
        }
//        // 전체 기부내역 불러오기
//        btnHistoryDoantion.setOnClickListener {
//            findNavController().navigate(R.id.action_donationFragment_to_donorHistoryFragment)
//        }
//
//        // 아이들 기부금 사용내역
//        btnHistoryChild.setOnClickListener {
//            findNavController().navigate(R.id.action_donationFragment_to_childHistoryFragment)
//        }
//
//        // 기부하기
//        btnDonte.setOnClickListener {
//            val userBalance = userViewModel.userBalance.value ?: 0
//            donationViewModel.doDonate(userBalance, true)
//        }
    }
}