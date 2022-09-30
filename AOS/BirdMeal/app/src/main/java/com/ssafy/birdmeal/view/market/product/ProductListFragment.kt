package com.ssafy.birdmeal.view.market.product

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentProductListBinding
import com.ssafy.birdmeal.model.dto.ProductDto
import com.ssafy.birdmeal.view.market.CategoryListener
import com.ssafy.birdmeal.view.market.MarketViewModel
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel

class ProductListFragment : BaseFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    private val args by navArgs<ProductListFragmentArgs>()
    private var categorySeq = -1
    private var categoryName = ""

    private var searchList = mutableListOf<ProductDto>()
    private lateinit var productListAll : List<ProductDto>

    private lateinit var productAdapter : ProductListAdapter

    override fun init() {
        this.categorySeq = args.categorySeq
        this.categoryName = args.categoryName

        initRecyclerView()

        initClickListener()

        initViewModelCallBack()

        initSearch()
    }

    private fun initRecyclerView() {
        val categoryAdapter = CategoryHorizonAdapter(categoryListener)
        productAdapter = ProductListAdapter(productListener)

        binding.apply {
            rvCategoryHorizon.adapter = categoryAdapter
            rvProductList.adapter = productAdapter
            productCnt = shoppingViewModel.productCnt.value
        }
    }

    private fun initClickListener() = with(binding){
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        ivShoppingCart.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_shoppingCartFragment)
        }
    }

    private fun initViewModelCallBack() = with(marketViewModel){
        errorMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }
        listSuccessEvent.observe(viewLifecycleOwner){
            productListAll = productList.value
            productAdapter.submitList(productListAll)
        }
    }

    private val categoryListener = object : CategoryListener {
        override fun onItemClick(seq: Int, name: String) { // 상품 카테고리 seq에 따른 상품 목록 조회 api 재호출
            categorySeq = seq
            categoryName = name
            marketViewModel.getProductList(categorySeq)
            binding.tvToolbar.text = categoryName
        }
    }

    private val productListener = object : ProductListener {
        override fun onItemClick(productSeq: Int) {  // 상품 상세정보로 이동
            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productSeq)
            findNavController().navigate(action)
        }
    }

    private fun initSearch(){
        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 실시간 검색
                binding.apply {
                    searchList.clear() // 검색 리스트 초기화
                    searchList.addAll(
                        productListAll.filter {
                            it.productName.contains(p0.toString())
                        }
                    )
                    productAdapter.submitList(searchList.toMutableList())
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    override fun onStart() {
        super.onStart()

        if(categorySeq > 0){ // 파라미터가 잘 전달된 경우
            marketViewModel.getProductList(categorySeq)
        } else { // 파라미터가 전달되지 않은 경우
            showToast("상품 카테고리Seq 전달 받지 못했습니다.")
        }
        binding.apply {
            marketVM = marketViewModel
            tvToolbar.text = categoryName
        }
    }

}