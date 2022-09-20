package com.ssafy.birdmeal.view.login.join

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentJoinBinding
import com.ssafy.birdmeal.view.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun init() {
        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() = with(binding) {

        // 일반 회원가입
        layoutGeneral.setOnClickListener {
            // 회원가입 api 호출
            loginViewModel.join(false)
        }

        // 아동 회원가입입
        layoutChild.setOnClickListener {
            // 카드 인증 페이지 이동
            findNavController().navigate(R.id.action_joinFragment_to_cardFragment)
        }
    }

    private fun initViewModelCallBack() = with(loginViewModel) {
        errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
        // 회원가입 성공하면 홈화면으로
        joinSuccessMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            Intent(requireContext(), MainActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }
    }

}