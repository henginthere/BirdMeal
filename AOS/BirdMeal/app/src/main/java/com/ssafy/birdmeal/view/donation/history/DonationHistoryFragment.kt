package com.ssafy.birdmeal.view.donation.history

import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonationHistoryBinding
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor

class DonationHistoryFragment :
    BaseFragment<FragmentDonationHistoryBinding>(R.layout.fragment_donation_history) {

    lateinit var childHistoryFragment: ChildHistoryFragment
    lateinit var donorHistoryFragment: DonorHistoryFragment

    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)

        initFragmentManager()

        initChip()

        initClickListener()
    }

    private fun initFragmentManager() = with(binding) {
        childHistoryFragment = ChildHistoryFragment()
        donorHistoryFragment = DonorHistoryFragment()

        childFragmentManager.beginTransaction().replace(R.id.container_view, childHistoryFragment)
            .commit()
    }

    private fun initChip() = with(binding) {
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val chip = chipGroup.children.find { (it as Chip).isChecked }
            val tag = (chip as Chip).tag

            when (tag) {
                "1" -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_view, childHistoryFragment)
                        .commit()
                }
                "2" -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_view, donorHistoryFragment)
                        .commit()
                }
            }
        }
    }

    private fun initClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}