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
        // marketViewModel.getProductList(categorySeq)

        val adapter = CategoryHorizonAdapter(listener)
        adapter.submitList(marketViewModel.categoryList)
        binding.rvCategoryHorizon.adapter = adapter

        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initViewModelCallBack() {
        marketViewModel.errorMsgEvent.observe(this){
            showToast(it)
        }
    }

    private val listener = object : CategoryListener {
        override fun onItemClick(categorySeq: Int) { // 상품 카테고리 seq에 따른 상품 목록 조회 api 재호출
            showToast("재호출 합니다.")
        }
    }

}