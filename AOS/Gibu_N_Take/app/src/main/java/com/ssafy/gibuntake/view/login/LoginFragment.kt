package com.ssafy.gibuntake.view.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.gibuntake.MainActivity
import com.ssafy.gibuntake.R
import com.ssafy.gibuntake.base.BaseFragment
import com.ssafy.gibuntake.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val userVM by viewModels<LoginViewModel>()

    override fun init() {
        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() {
        // 구글 소셜 로그인 버튼 클릭시
        googleSignIn()
    }

    private fun initViewModelCallBack() {
        userVM.errMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }
        userVM.loginMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
            // 홈 화면 이동
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        userVM.joinMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
            // 회원가입 화면 이동
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
    }

    private fun googleSignIn(){

    }

}