package com.ssafy.birdmeal.view.donation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.UserViewModel
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.strategy.Strategy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : BaseFragment<FragmentDonationBinding>(R.layout.fragment_donation) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.userVM = userViewModel
        binding.donationVM = donationViewModel
        binding.tvBalance.apply {
            animationDuration = 2000L
            charStrategy = Strategy.NormalAnimation()
            addCharOrder(CharOrder.Alphabet)
            animationInterpolator = AccelerateInterpolator()
            addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    Log.d(TAG, "onAnimationEnd: ")
                }
            })
        }

        initViewModelCallBack()

        initClickListener()
    }

    override fun onStart() {
        super.onStart()
        donationViewModel.getDonationAmount()
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
                tvBalance.setText(it)
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