package com.ssafy.birdmeal.view.market.product.detail

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentProductDetailBinding
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.view.market.MarketViewModel
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(R.layout.fragment_product_detail) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    private val args by navArgs<ProductDetailFragmentArgs>()
    private var productSeq = -1

    override fun init() {
        this.productSeq = args.productSeq
        if(productSeq > 0){ // 파라미터가 잘 전달된 경우
            marketViewModel.getProduct(productSeq)
        } else { // 파라미터가 전달되지 않은 경우
            showToast("상품 정보를 전달받지 못했습니다.")
        }

        binding.productCnt = shoppingViewModel.productCnt.value

        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener { // 툴바 뒤로가기
            findNavController().popBackStack()
        }
        ivShoppingCart.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailFragment_to_shoppingCartFragment)
        }
        layoutSellerInfo.setOnClickListener { // 판매자 정보 - 상품
            val action = ProductDetailFragmentDirections.actionProductDetailFragmentToSellerDetailFragment(binding.product!!.sellerSeq)
            findNavController().navigate(action)
        }
        btnBuy.setOnClickListener { // 구매하기 버튼
            val dialog = BuyBottomSheetDialog(requireContext(), marketViewModel.product.value, listener)
            dialog.show()
        }
    }

    private fun initViewModelCallBack() {
        marketViewModel.detailSuccessEvent.observe(viewLifecycleOwner){
            binding.product = marketViewModel.product.value
        }
    }

    private val listener = object : BuyDialogListener { // 장바구니에 담기
        override fun onItemClick(cart: CartEntity) {
            val action = ProductDetailFragmentDirections.actionProductDetailFragmentToShoppingCartFragment(cart)
            findNavController().navigate(action) // 장바구니 이동
        }
    }

}