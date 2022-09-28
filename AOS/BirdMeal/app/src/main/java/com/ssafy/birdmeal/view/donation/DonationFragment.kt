package com.ssafy.birdmeal.view.donation

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : BaseFragment<FragmentDonationBinding>(R.layout.fragment_donation) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.userVM = userViewModel
        binding.donationVM = donationViewModel
        donationViewModel.getDonationAmount()

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
        }
    }

    private fun initClickListener() = with(binding) {

        // 기부하기 페이지로 가기
        btnDonte.setOnClickListener {
            findNavController().navigate(R.id.action_donationFragment_to_donateFragment)
        }

        // 자세히 보기 버튼튼
        btnDetail.setOnClickListener {
            Log.d("TAG", "initClickListener: ")
            findNavController().navigate(R.id.action_donationFragment_to_donationHistoryFragment)
        }

        btnMakeCard.setOnClickListener {
            Log.d(TAG, "initClickListener: " + "my mind")
            donationViewModel.insertPhotoCard();
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
    }
}