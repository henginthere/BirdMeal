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
        binding.userVM = userViewModel

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {
    }

    private fun initClickListener() = with(binding) {
        btnModifyUser.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_editProfileFragment)
        }
    }

}