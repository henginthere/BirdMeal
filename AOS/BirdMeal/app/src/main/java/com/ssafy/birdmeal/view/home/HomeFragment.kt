package com.ssafy.birdmeal.view.home

import android.content.Intent
import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentHomeBinding
import com.ssafy.birdmeal.view.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        getUserInfo()

        initViewModelCallBack()

        initClickListener()
    }

    private fun getUserInfo() {
        userViewModel.getUserInfo()
    }

    private fun initViewModelCallBack() = with(userViewModel) {
        errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        successMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        user.observe(viewLifecycleOwner) { user ->
            // EOA 없는 경우
            if (user.userEoa == null) {
                // 지갑 or 개입키 등록 다이얼로그 띄움
            }
        }
    }

    private fun initClickListener() {
        binding.btnShowDonate.setOnClickListener { // 기부화면으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_donationFragment)
        }
        binding.btnShowMarket.setOnClickListener { // 마켓화면으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
    }

}