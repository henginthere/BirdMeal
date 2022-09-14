package com.ssafy.birdmeal.view.market

import androidx.fragment.app.viewModels
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val categoryVM by viewModels<CategoryViewModel>()

    override fun init() {
        val adapter = CategoryAdapter()
        // binding.rvCategory.adapter = adapter

        initClickListener()

        initViewModelCallBack()
    }

    private fun initClickListener() {

    }

    private fun initViewModelCallBack() {

    }
}