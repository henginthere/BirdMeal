package com.ssafy.birdmeal.view.my_page.nft.detail

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentNftDetailBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel

class NftDetailFragment : BaseFragment<FragmentNftDetailBinding>(R.layout.fragment_nft_detail) {

    private val nftViewModel by activityViewModels<NFTViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.nftVM = nftViewModel

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}