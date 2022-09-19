package com.ssafy.birdmeal.view.market.product.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentSellerDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerDetailFragment : BaseFragment<FragmentSellerDetailBinding>(R.layout.fragment_seller_detail) {

    private val sellerViewModel by viewModels<SellerViewModel>()
    private val args by navArgs<SellerDetailFragmentArgs>()
    private var sellerSeq = -1

    override fun init() {
        this.sellerSeq = args.sellerSeq
        if(sellerSeq > 0){
            sellerViewModel.getSellerInfo(sellerSeq)
        } else {
            showToast("sellerSeq 전달받지 못했습니다.")
        }

        initClickListener()

        initViewModelCallBack()
    }

    private fun initViewModelCallBack(){
        sellerViewModel.successMsgEvent.observe(viewLifecycleOwner){
            binding.seller = sellerViewModel.seller.value
        }
        sellerViewModel.errMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            ivShoppingCart.setOnClickListener {
                findNavController().navigate(R.id.action_sellerDetailFragment_to_shoppingCartFragment)
            }
        }
    }

}