package com.ssafy.birdmeal.view.login.join

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentJoinBinding
import com.ssafy.birdmeal.view.login.LoginViewModel

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val loginVM by activityViewModels<LoginViewModel>()

    override fun init() {
        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() = with(binding) {

        // 일반 회원가입
        containerGeneral.setOnClickListener {
            // 회원가입 api 호출
            loginVM.join("buyer")
        }

        // 아동 회원가입입
        containerChild.setOnClickListener {
            // 카드 인증 페이지 이동
        }
    }

    private fun initViewModelCallBack() = with(loginVM) {
        errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
        // 회원가입 성공하면 홈화면으로
        successMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            Intent(requireContext(), MainActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }
    }

}