package com.ssafy.birdmeal.view.market.shopping.cart

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentShoppingCartBinding
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingCartFragment : BaseFragment<FragmentShoppingCartBinding>(R.layout.fragment_shopping_cart) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()
    private val args by navArgs<ShoppingCartFragmentArgs>()
    private lateinit var cart : CartEntity
    private var adapter = ShoppingCartAdapter()

    override fun init() {
        if(args.cart != null){ // 구매하기로 진입한 경우
            this.cart = args.cart!!
            shoppingViewModel.insert(cart)
        } else { // 비구매 진입

        }
        shoppingViewModel.getCartList()
        binding.rvCartList.adapter = adapter

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack(){
        shoppingViewModel.errMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }

        lifecycleScope.launch {
            shoppingViewModel.productList.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    private fun initClickListener(){
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnBuy.setOnClickListener {
                findNavController().navigate(R.id.action_shoppingCartFragment_to_orderFragment)
            }
        }
    }

}