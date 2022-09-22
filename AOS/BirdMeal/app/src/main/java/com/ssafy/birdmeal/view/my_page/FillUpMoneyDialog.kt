package com.ssafy.birdmeal.view.my_page

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogFillUpMoneyBinding

class FillUpMoneyDialog(context: Context, private val listener: FillUpMoneyListener): Dialog(context) {

    private lateinit var binding : DialogFillUpMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_fill_up_money,
            null, false
        )
        setContentView(binding.root)

        // 배경 투명하게 하기
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            tvCharge.setOnClickListener {
                listener.onItemClick(etAmount.text.toString().toInt()) // 수정하려는 수량 보내주기
                dismiss()
            }
        }
    }

}