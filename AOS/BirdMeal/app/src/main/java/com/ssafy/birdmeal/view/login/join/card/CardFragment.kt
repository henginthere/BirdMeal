package com.ssafy.birdmeal.view.login.join.card

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCardBinding
import com.ssafy.birdmeal.view.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding>(R.layout.fragment_card) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun init() {
        binding.loginVM = loginViewModel

        initViewModelCallBack()
    }

    private fun initViewModelCallBack() = with(loginViewModel) {
        errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        // 카드 인식 성공
        childSuccessMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            // 회원 가입 로직 호출
            loginViewModel.join("child")
        }

        // 카드 인식 실패
        childFailMsgEvent.observe(viewLifecycleOwner) {
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