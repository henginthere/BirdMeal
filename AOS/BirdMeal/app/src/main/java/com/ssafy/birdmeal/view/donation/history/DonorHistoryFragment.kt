package com.ssafy.birdmeal.view.donation.history

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
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

        initChip()
    }

    private fun initAdapter() = with(binding) {
        rvDonationHistroy.adapter = donationHistoryListAdapter
    }

    private fun initViewModelCallBack() = with(donationViewModel) {
        donateMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        donationAllHistoryList.observe(viewLifecycleOwner) {
            donationHistoryListAdapter.submitList(it)
        }
    }

    private fun initChip() = with(binding) {
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val chip = chipGroup.children.find { (it as Chip).isChecked }
            chipGroup.children.forEach {
                if ((it as Chip).isChecked) {
                    it.textSize = 20F
                } else {
                    it.textSize = 16F
                }
            }
            val tag = (chip as Chip).tag

            when (tag) {
                // 전체 기부내역
                "1" -> {
                    donationHistoryListAdapter.submitList(donationViewModel.donationAllHistoryList.value)
                }
                // 직접 기부내역
                "2" -> {
                    donationHistoryListAdapter.submitList(donationViewModel.donationAllHistoryList.value?.filter { it.donationType })
                }
                // 간접 기부내역
                "3" -> {
                    donationHistoryListAdapter.submitList(donationViewModel.donationAllHistoryList.value?.filter { !it.donationType })
                }
            }
        }
    }
}