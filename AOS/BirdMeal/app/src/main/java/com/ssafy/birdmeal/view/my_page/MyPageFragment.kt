package com.ssafy.birdmeal.view.my_page

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyPageBinding
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.login.LoginActivity

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        userViewModel.getUserTokenValue()
        binding.userVM = userViewModel

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            successMsgEvent.observe(viewLifecycleOwner){
                showToast(it)
            }
        }
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

        // 내 주문내역 보기
        btnOrderHistory.setOnClickListener{
            findNavController().navigate(R.id.action_myPageFragment_to_myOrderHistoryFragment)
        }

        // 충전하기 버튼 클릭
        btnFillUpMoney.setOnClickListener {
            val dialog = FillUpMoneyDialog(requireContext(), listener)
            dialog.show()
        }

        // 로그아웃
        btnLogout.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build()
            val client = GoogleSignIn.getClient(requireActivity(), gso)
            client.signOut().addOnCompleteListener {
                showToast("로그아웃 완료")
            }
            Intent(requireContext(), LoginActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    // 토큰 충전 다이얼로그 리스너
    private val listener = object : FillUpMoneyListener{
        override fun onItemClick(requestMoney: Int) {
            userViewModel.fillUpToken(requestMoney)
        }
    }

}