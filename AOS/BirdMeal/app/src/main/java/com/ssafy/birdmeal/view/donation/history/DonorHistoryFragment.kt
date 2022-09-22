package com.ssafy.birdmeal.view.donation.history

import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonorHistoryBinding
import com.ssafy.birdmeal.view.donation.DonationViewModel

class DonorHistoryFragment :
    BaseFragment<FragmentDonorHistoryBinding>(R.layout.fragment_donor_history) {

    private val donationViewModel by activityViewModels<DonationViewModel>()
    private val donationHistoryListAdapter by lazy { DonationHistoryListAdapter() }


    override fun init() {
        binding.donationVM = donationViewModel
        donationViewModel.getAllDonationHistory()

        initAdapter()

        initViewModelCallBack()
    }

    private fun initAdapter() = with(binding) {
        rvDonationHistroy.adapter = donationHistoryListAdapter
    }

    private fun initViewModelCallBack() = with(donationViewModel) {
        donateMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }
}