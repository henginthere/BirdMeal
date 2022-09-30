package com.ssafy.birdmeal.view.my_page.edit

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.home.UserViewModel

class EditProfileFragment :
    BaseFragment<com.ssafy.birdmeal.databinding.FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        binding.userVM = userViewModel

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() = with(userViewModel) {
        userUpdateMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            findNavController().popBackStack()
        }
    }

    private fun initClickListener() = with(binding) {

        // 주소 검색
        btnSearchAddress.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_searchAddressFragment)
        }

        btnSave.setOnClickListener {
            Log.d(TAG, "initClickListener: 수정하기 클릭")
            userViewModel.updateUserProfile()
        }
    }

}