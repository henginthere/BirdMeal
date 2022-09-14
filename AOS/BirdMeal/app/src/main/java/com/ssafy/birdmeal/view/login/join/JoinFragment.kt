package com.ssafy.birdmeal.view.login.join

import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentJoinBinding

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener() = with(binding) {

        // 일반 회원가입
        containerGeneral.setOnClickListener {
            // 회원가입 api 호출
        }

        // 아동 회원가입입
        containerChild.setOnClickListener {
            // 카드 인증 페이지 이동
        }
    }

}