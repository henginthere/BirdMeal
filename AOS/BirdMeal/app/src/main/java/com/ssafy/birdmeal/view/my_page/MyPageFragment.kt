package com.ssafy.birdmeal.view.my_page

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyPageBinding
import com.ssafy.birdmeal.view.home.UserViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        userViewModel.getUserTokenValue()
        binding.userVM = userViewModel

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {

    }

    private fun initClickListener() = with(binding) {
        // 회원정보 수정
        btnModifyUser.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_editProfileFragment)
        }

        // 내 기부내역 보기
        btnDonationHistory.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myDonationHistoryFragment)
        }
    }

}