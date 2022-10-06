package com.ssafy.birdmeal.view.my_page.history.donation

import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyDonationHistoryBinding
import com.ssafy.birdmeal.view.donation.DonationViewModel
import com.ssafy.birdmeal.view.donation.history.DonationHistoryListAdapter

class MyDonationHistoryFragment :
    BaseFragment<FragmentMyDonationHistoryBinding>(R.layout.fragment_my_donation_history) {

    private val donationViewModel by activityViewModels<DonationViewModel>()
    private val donationHistoryListAdapter by lazy { DonationHistoryListAdapter() }

    override fun init() {
        binding.donationVM = donationViewModel
        donationViewModel.getMyDonationHistory()

        initAdapter()

        initViewModelCallBack()
    }

    private fun initAdapter() = with(binding) {
        rvDonationHistroy.adapter = donationHistoryListAdapter
    }

    private fun initViewModelCallBack() = with(donationViewModel) {
        donateMsgEvent.observe(viewLifecycleOwner) {
//            showToast(it)
        }

        donationMyHistoryList.observe(viewLifecycleOwner){
            donationHistoryListAdapter.submitList(it)
        }
    }

}