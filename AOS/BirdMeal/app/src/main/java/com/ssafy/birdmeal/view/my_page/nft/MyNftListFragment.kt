package com.ssafy.birdmeal.view.my_page.nft

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyNftListBinding
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel

class MyNftListFragment : BaseFragment<FragmentMyNftListBinding>(R.layout.fragment_my_nft_list) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val nftViewModel by activityViewModels<NFTViewModel>()
    private val myNftListAdapter by lazy { MyNftListAdapter() }


    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)
        nftViewModel.getMyNft()

        initAdapter()

        initViewModelCallBack()

        initClickListener()
    }

    private fun initAdapter() = with(binding) {
        rvNft.adapter = myNftListAdapter
    }

    private fun initViewModelCallBack() {
        nftViewModel.apply {
            myNftList.observe(viewLifecycleOwner) {
                myNftListAdapter.submitList(it)
            }
        }
    }

    private fun initClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}