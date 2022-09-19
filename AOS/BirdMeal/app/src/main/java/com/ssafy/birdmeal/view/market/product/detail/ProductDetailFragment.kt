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
            marketViewModel.getProduct(productSeq)
        } else { // 파라미터가 전달되지 않은 경우
            showToast("productSeq 전달받지 못했스빈다.")
        }

        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener { // 툴바 뒤로가기
                findNavController().popBackStack()
            }
            ivShoppingCart.setOnClickListener {
                findNavController().navigate(R.id.action_productDetailFragment_to_shoppingCartFragment)
            }
            tvSellerInfo.setOnClickListener { // 판매자 정보 - 상품
                val action = ProductDetailFragmentDirections.actionProductDetailFragmentToSellerDetailFragment(1)
                findNavController().navigate(action)
            }
            btnBuy.setOnClickListener { // 구매하기 버튼
                val dialog = BuyBottomSheetDialog(requireContext(), marketViewModel.product.value, listener)
                dialog.show()
            }
        }
    }

    private fun initViewModelCallBack() {
        marketViewModel.successMsgEvent.observe(viewLifecycleOwner){
            binding.product = marketViewModel.product.value
        }
    }

    private val listener = object : BuyDialogListener { // 장바구니에 담기
        override fun onItemClick(productSeq: Int) {
            // RoomDB에 담는 로직

            findNavController().navigate(R.id.action_productDetailFragment_to_shoppingCartFragment) // 장바구니 이동
        }
    }

}