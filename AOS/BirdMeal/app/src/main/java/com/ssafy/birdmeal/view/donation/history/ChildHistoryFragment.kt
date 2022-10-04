package com.ssafy.birdmeal.view.donation.history

import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentChildHistoryBinding
import com.ssafy.birdmeal.view.donation.DonationViewModel


class ChildHistoryFragment :
    BaseFragment<FragmentChildHistoryBinding>(R.layout.fragment_child_history) {

    private val donationViewModel by activityViewModels<DonationViewModel>()
    private val childHistoryListAdapter by lazy { ChildHistoryListAdapter() }

    override fun init() {
        binding.donationVM = donationViewModel
        donationViewModel.getChildOrderHistory()
//        donationViewModel.getChildAmount()

        initAdapter()

        initViewModelCallBack()
    }

    private fun initAdapter() = with(binding) {
        rvChildHistroy.adapter = childHistoryListAdapter
    }

    private fun initViewModelCallBack() = with(donationViewModel) {
        donateMsgEvent.observe(viewLifecycleOwner) {
//            showToast(it)
        }
        orderChildHistoryList.observe(viewLifecycleOwner) {
            childHistoryListAdapter.submitList(it)
        }
    }
}