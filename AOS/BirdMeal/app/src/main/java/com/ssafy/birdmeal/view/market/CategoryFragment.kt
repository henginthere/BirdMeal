package com.ssafy.birdmeal.view.market

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val marketViewModel by activityViewModels<MarketViewModel>()

    override fun init() {
        marketViewModel.getCategoryList()
        val adapter = CategoryGridAdapter(listener)

        binding.apply {
            marketVM = marketViewModel
            rvCategoryGrid.adapter = adapter
        }

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {
        marketViewModel.errorMsgEvent.observe(this){
            showToast(it)
        }
    }

    private fun initClickListener() {
        binding.ivShoppingCart.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_shoppingCartFragment)
        }
    }

    private val listener = object : CategoryListener{
        override fun onItemClick(categorySeq: Int, categoryName: String) {
            val action = CategoryFragmentDirections.actionCategoryFragmentToProductListFragment(categorySeq, categoryName)
            findNavController().navigate(action)
        }
    }

}