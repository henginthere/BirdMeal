package com.ssafy.birdmeal.view.market

import androidx.fragment.app.viewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val categoryVM by viewModels<CategoryViewModel>()

    override fun init() {
        val adapter = CategoryAdapter()
        adapter.submitList(categoryVM.categoryList)
        binding.rvCategory.adapter = adapter

        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() {
        
    }

    private fun initViewModelCallBack() {
        categoryVM.errorMsgEvent.observe(this){
            showToast(it)
        }
    }

}