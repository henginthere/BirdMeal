package com.ssafy.birdmeal.view.home

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentHomeBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.donation.DonationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)

        userViewModel.getUserInfo()

        checkWallet()

        initViewModelCallBack()

        initClickListener()
    }

    private fun checkWallet() {
        userViewModel.checkPrivateKey(requireContext())
    }

    private fun initViewModelCallBack() = with(userViewModel) {
        errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        userInfoMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        walletMsgEvent.observe(viewLifecycleOwner) {
            // 지갑이 이미 있는 경우
            if (it) {
                findNavController().navigate(R.id.action_homeFragment_to_donationFragment)
            }
            // 지갑이 없는 경우
            else {
                findNavController().navigate(R.id.action_homeFragment_to_createWalletFragment)
            }
        }

        // 인증서 준비된 경우
        userViewModel.credentials.observe(viewLifecycleOwner) {
            (requireActivity().application as ApplicationClass).initContract(it)

            donationViewModel.getDonationAmount()
            showToast("인증서 준비 완료")
        }
    }

    private fun initClickListener() {
//        binding.btnShowDonate.setOnClickListener { // 기부화면으로 이동
//            findNavController().navigate(R.id.action_homeFragment_to_donationFragment)
//        }
//        binding.btnShowMarket.setOnClickListener { // 마켓화면으로 이동
//            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
//        }
    }

}