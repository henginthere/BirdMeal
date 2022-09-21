package com.ssafy.birdmeal.view.donation

import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationBinding
import com.ssafy.birdmeal.view.home.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFragment : BaseFragment<FragmentDonationBinding>(R.layout.fragment_donation) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() = with(binding) {
        userViewModel.apply {
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }

        donationViewModel.apply {
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }

            // 불러온 전체 기부액 표시
            donationMsgEvent.observe(viewLifecycleOwner) {
                tvBalance.text = it
            }

            // 기부 완료
            donateMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }
    }

    private fun initClickListener() {
    }
}