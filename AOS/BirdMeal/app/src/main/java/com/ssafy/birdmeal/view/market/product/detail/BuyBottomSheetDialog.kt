package com.ssafy.birdmeal.view.market.product.detail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogBuyBottomSheetBinding
import com.ssafy.birdmeal.model.dto.ProductDto

class BuyBottomSheetDialog(context: Context, private val product: ProductDto, private val listener : BuyDialogListener)
    : BottomSheetDialog(context) {

    private lateinit var binding: DialogBuyBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_buy_bottom_sheet, null, false)
        setContentView(binding.root)

        binding.productDto = product

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            var cnt = tvBuyCnt.text.toString().toInt()

            btnCntMinus.setOnClickListener {
                if(cnt == 1){
                    Toast.makeText(context, "최소 수량은 1개 입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    cnt--
                    tvBuyCnt.text = cnt.toString()
                }
            }
            btnCntPlus.setOnClickListener {
                cnt++
                tvBuyCnt.text = cnt.toString()
            }
            btnBuy.setOnClickListener { // 장바구니에 담기
                listener.onItemClick(product.productSeq)
                dismiss()
            }
        }
    }

}