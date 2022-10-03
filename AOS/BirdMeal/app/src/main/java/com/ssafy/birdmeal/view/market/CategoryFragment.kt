package com.ssafy.birdmeal.view.market

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCategoryBinding
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)

        marketViewModel.getCategoryList()
        shoppingViewModel.getCartList(userViewModel.user.value!!.userRole)

        binding.apply {
            marketVM = marketViewModel
            rvCategoryGrid.adapter = CategoryGridAdapter(listener)
        }

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {
        marketViewModel.apply {
            errorMsgEvent.observe(viewLifecycleOwner){
                showToast(it)
            }
        }

        shoppingViewModel.updateSuccessMsgEvent.observe(viewLifecycleOwner){
            binding.productCnt =  shoppingViewModel.productCnt.value
        }
    }

    private fun initClickListener() = with(binding) {
        ivShoppingCart.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_shoppingCartFragment)
        }
        // 상품 전체검색
        btnSearch.setOnClickListener {
            val name = etSearch.text.toString()
            if(name.isEmpty()){
                showToast("검색어를 입력해 주세요.")
            } else {
                val action = CategoryFragmentDirections.actionCategoryFragmentToProductListFragment(0, name)
                findNavController().navigate(action)

                etSearch.text.clear()
            }
        }

    }

    private val listener = object : CategoryListener{
        override fun onItemClick(categorySeq: Int, categoryName: String) {
            val action = CategoryFragmentDirections.actionCategoryFragmentToProductListFragment(categorySeq, categoryName)
            findNavController().navigate(action)
        }
    }

}