package com.ssafy.birdmeal.view.market.product

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentProductListBinding
import com.ssafy.birdmeal.view.market.CategoryListener
import com.ssafy.birdmeal.view.market.MarketViewModel

class ProductListFragment : BaseFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private val args by navArgs<ProductListFragmentArgs>()
    private var categorySeq = -1

    override fun init() {
        this.categorySeq = args.categorySeq
        if(categorySeq > 0){ // 파라미터가 잘 전달된 경우
            // marketViewModel.getProductList(categorySeq)
        } else { // 파라미터가 전달되지 않은 경우
            showToast("상품 카테고리 정보를 전달 받지 못했습니다.")
        }

        binding.marketVM = marketViewModel

        initRecyclerView()

        initClickListener()

        initViewModelCallBack()
    }

    private fun initRecyclerView() {
        val categoryAdapter = CategoryHorizonAdapter(categoryListener)
        categoryAdapter.submitList(marketViewModel.categoryList)

        val productAdapter = ProductListAdapter(productListener)
        productAdapter.submitList(marketViewModel.productList)

        binding.apply {
            rvCategoryHorizon.adapter = categoryAdapter
            rvProductList.adapter = productAdapter
        }
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            ivShoppingCart.setOnClickListener {
                findNavController().navigate(R.id.action_productListFragment_to_shoppingCartFragment)
            }
        }
    }

    private fun initViewModelCallBack() {
        marketViewModel.errorMsgEvent.observe(this){
            showToast(it)
        }
    }

    private val categoryListener = object : CategoryListener {
        override fun onItemClick(categorySeq: Int) { // 상품 카테고리 seq에 따른 상품 목록 조회 api 재호출
            // val action = CategoryFragmentDirections.actionCategoryFragmentToProductListFragment(categorySeq)
            // findNavController().navigate(action)
            showToast("재호출 합니다.")
        }
    }

    private val productListener = object : ProductListener {
        override fun onItemClick(productSeq: Int) {  // 상품 상세정보로 이동
            // val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productSeq)
            // findNavController().navigate(action)
            findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
        }
    }

}