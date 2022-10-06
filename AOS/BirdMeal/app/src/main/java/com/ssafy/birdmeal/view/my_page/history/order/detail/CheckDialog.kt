package com.ssafy.birdmeal.view.my_page.history.order.detail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogCheckOrderStateBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.model.response.OrderTHashResponse

class CheckDialog(context: Context,private val listener: CheckDialogListener, private val orderDetailSeq: Int): BottomSheetDialog(context) {
    private lateinit var binding : DialogCheckOrderStateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_check_order_state,
            null, false
        )
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            btnYes.setOnClickListener {
                listener.onItemClick(orderDetailSeq)
                dismiss()
            }
            btnNo.setOnClickListener {
                dismiss()
            }
        }
    }
}