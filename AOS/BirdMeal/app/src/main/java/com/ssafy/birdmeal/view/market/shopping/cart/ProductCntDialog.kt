package com.ssafy.birdmeal.view.market.shopping.cart

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.databinding.DataBindingUtil
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogProductCntBinding

class ProductCntDialog(context: Context, private val listener: ProductCntDialogListener): Dialog(context) {

    private lateinit var binding : DialogProductCntBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_product_cnt,
            null, false
        )
        setContentView(binding.root)

        binding.numberPicker.apply {
            minValue = 1
            maxValue = 10
            // 순환 안되게 막기
            wrapSelectorWheel = false
            // 키보드로 수정 막기
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        // 배경 투명하게 하기
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            tvConfirm.setOnClickListener {
                listener.onItemClick(numberPicker.value) // 수정하려는 수량 보내주기
                dismiss()
            }
        }
    }

}