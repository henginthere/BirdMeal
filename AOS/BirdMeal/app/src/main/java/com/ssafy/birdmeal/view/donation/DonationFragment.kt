package com.ssafy.birdmeal.view.donation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.*
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.donation.nft.CompletedMintingDialog
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingMintingDialog
import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.strategy.Strategy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : BaseFragment<FragmentDonationBinding>(R.layout.fragment_donation) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()
    private val nftViewModel by activityViewModels<NFTViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.userVM = userViewModel
        binding.donationVM = donationViewModel
//        binding.tvBalance.apply {
//            animationDuration = 2000L
//            charStrategy = Strategy.NormalAnimation()
//            addCharOrder(CharOrder.Alphabet)
//            animationInterpolator = AccelerateInterpolator()
//            addAnimatorListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator?) {
//                    super.onAnimationEnd(animation)
//                    Log.d(TAG, "onAnimationEnd: ")
//                }
//            })
//        }

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

//            user.observe(viewLifecycleOwner) {
//                if (it.userIsMint) {
//                    mintingDialog()
//                }
//            }
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

        nftViewModel.apply {
            mintingMsgEvent.observe(viewLifecycleOwner) {
                loadingMintingDialog.dismiss()
                userViewModel.getUserInfo()
                CompletedMintingDialog(it).show(childFragmentManager, "CompletedMintingDialog")
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

        // 마음 전하기 버튼
        btnMakeCard.setOnClickListener {
            findNavController().navigate(R.id.action_donationFragment_to_canvasFragment)
//            Log.d(TAG, "initClickListener: " + "my mind")
//            donationViewModel.insertPhotoCard();
        }
    }

    // 민팅하라는 다이얼로그
    private fun mintingDialog() {
        AwesomeDialog.build(requireActivity())
            .title("따뜻한 마음 감사합니다")
            .body("아이들이 제작한 포토카드 NFT를 받을 수 있습니다")
            .icon(R.drawable.ic_photocard)
            .onPositive(text = "받기", buttonBackgroundColor = R.drawable.btn_round_10_green) {
                nftViewModel.getPhotoCardUrl()
                loadingMintingDialog.show(childFragmentManager, "loadingMintingDialog")
            }
            .onNegative(text = "취소", buttonBackgroundColor = R.drawable.btn_round_main_color) {

            }
    }
}