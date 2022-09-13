package com.ssafy.birdmeal.view.home

import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun init() {
        initClickListener()
    }

    private fun initClickListener(){
        binding.btnShowDonate.setOnClickListener { // 기부화면으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_donationFragment)
        }
        binding.btnShowMarket.setOnClickListener { // 마켓화면으로 이동
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
    }

}