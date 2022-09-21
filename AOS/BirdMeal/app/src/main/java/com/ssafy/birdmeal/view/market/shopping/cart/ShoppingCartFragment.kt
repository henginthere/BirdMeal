package com.ssafy.birdmeal.view.market.shopping.cart

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentShoppingCartBinding
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingCartFragment : BaseFragment<FragmentShoppingCartBinding>(R.layout.fragment_shopping_cart) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()
    private val args by navArgs<ShoppingCartFragmentArgs>()
    private lateinit var cart : CartEntity
    private lateinit var adapter : ShoppingCartAdapter

    override fun init() {
        if(args.cart != null){ // 구매하기로 진입한 경우
            this.cart = args.cart!!
            shoppingViewModel.insert(cart)
        }
        shoppingViewModel.getCartList()
        adapter = ShoppingCartAdapter(listener)

        binding.apply {
            shoppingVM = shoppingViewModel
            rvCartList.adapter = adapter
        }

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack(){
        shoppingViewModel.errMsgEvent.observe(viewLifecycleOwner){
            showToast(it)
        }

        lifecycleScope.launch {
            shoppingViewModel.productList.collect {
                Log.d(TAG, "initViewModelCallBack: $it")
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

    private val listener = object : ShoppingCartListener{
        override fun onDeleteClick(cart: CartEntity) {
            shoppingViewModel.delete(cart)
            showToast("장바구니에서 물품을 삭제했습니다.")
        }

        override fun onCntClick(item: CartEntity) {
            cart = item
            initProductCntDialog()
        }
    }

    private val dialogListener = object : ProductCntDialogListener{
        override fun onItemClick(cnt: Int) { // 바뀐 수량으로 room db 업데이트
            cart.productCount = cnt
            shoppingViewModel.update(cart)
        }
    }

    private fun initProductCntDialog(){
        val dialog = ProductCntDialog(requireContext(), dialogListener)
        dialog.show()
    }

}