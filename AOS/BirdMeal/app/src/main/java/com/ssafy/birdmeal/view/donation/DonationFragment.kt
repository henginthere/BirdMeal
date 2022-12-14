package com.ssafy.birdmeal.view.donation

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.*
import com.mikhaellopez.rxanimation.RxAnimation
import com.mikhaellopez.rxanimation.scale
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.DONATE_COMPLETED
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.donation.nft.CompletedMintingDialog
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingMintingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

            // ????????? ?????? ????????? ??????
            donationMsgEvent.observe(viewLifecycleOwner) {
                tvBalance.setText(it)
            }

            loadingMsgEvent.observe(viewLifecycleOwner) {
                when (it) {
                    // ????????? ?????? ???
                    DONATE_COMPLETED -> {
                        CompletedDonationDialog().show(
                            childFragmentManager,
                            "CompletedDonationDialog"
                        )
                    }
                }
            }

            // ?????? ????????? ???????????????
            animationMsgEvent.observe(viewLifecycleOwner) {
                lottie.playAnimation()
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    RxAnimation.from(ivMoney)
                        .scale(1.3f, duration = 1000L, reverse = true)
                        .subscribe()
                }
            }
        }

        nftViewModel.apply {
//            mintingMsgEvent.observe(viewLifecycleOwner) {
//                loadingMintingDialog.dismiss()
//                userViewModel.getUserInfo()
//                CompletedMintingDialog(it).show(childFragmentManager, "CompletedMintingDialog")
//            }
        }
    }

    private fun initClickListener() = with(binding) {

        // ???????????? ???????????? ??????
        btnDonte.setOnClickListener {
            findNavController().navigate(R.id.action_donationFragment_to_donateFragment)
        }

        // ????????? ?????? ?????????
        btnDetail.setOnClickListener {
            Log.d("TAG", "initClickListener: ")
            findNavController().navigate(R.id.action_donationFragment_to_donationHistoryFragment)
        }

        // ?????? ????????? ??????
        btnMakeCard.setOnClickListener {
            findNavController().navigate(R.id.action_donationFragment_to_canvasFragment)
//            Log.d(TAG, "initClickListener: " + "my mind")
//            donationViewModel.insertPhotoCard();
        }
    }

//    // ??????????????? ???????????????
//    private fun mintingDialog() {
//        AwesomeDialog.build(requireActivity())
//            .title("????????? ?????? ???????????????")
//            .body("???????????? ????????? ???????????? NFT??? ?????? ??? ????????????")
//            .icon(R.drawable.ic_photocard)
//            .onPositive(text = "??????", buttonBackgroundColor = R.drawable.btn_round_10_green) {
//                nftViewModel.getPhotoCardUrl()
//                loadingMintingDialog.show(childFragmentManager, "loadingMintingDialog")
//            }
//            .onNegative(text = "??????", buttonBackgroundColor = R.drawable.btn_round_main_color) {
//
//            }
//    }
}