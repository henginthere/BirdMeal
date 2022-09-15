package com.ssafy.birdmeal.view.market.product.detail

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentProductDetailBinding
import com.ssafy.birdmeal.view.market.MarketViewModel

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private val args by navArgs<ProductDetailFragmentArgs>()
    private var productSeq = -1

    override fun init() {
        this.productSeq = args.productSeq
        if(productSeq > 0){ // 파라미터가 잘 전달된 경우
            // marketViewModel.getProduct(productSeq)
        } else { // 파라미터가 전달되지 않은 경우
            showToast("상품 정보를 전달 받지 못했습니다.")
        }

        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener { // 툴바 뒤로가기
                findNavController().popBackStack()
            }
            tvSellerInfo.setOnClickListener { // 판매자 정보
                // val action = ProductDetailFragmentDirections.actionProductDetailFragmentToSellerDetailFragment(marketViewModel.product.value.sellerSeq)
                // findNavController().navigate(action)
                findNavController().navigate(R.id.action_productDetailFragment_to_sellerDetailFragment)
            }
            btnBuy.setOnClickListener { // 구매하기 버튼

            }
        }
    }

    private fun initViewModelCallBack() {
        marketViewModel.successMsgEvent.observe(viewLifecycleOwner){
            binding.productDto = marketViewModel.product.value
        }
    }

}