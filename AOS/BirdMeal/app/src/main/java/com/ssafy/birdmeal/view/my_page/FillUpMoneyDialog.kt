package com.ssafy.birdmeal.view.my_page

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogFillUpMoneyBinding
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingFillUpDialog

class FillUpMoneyDialog(context: Context, private val listener: FillUpMoneyListener) :
    DialogFragment() {

    private lateinit var binding: DialogFillUpMoneyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fill_up_money,
            container,
            false
        )
        // 배경 투명하게 바꿔줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            tvCharge.setOnClickListener {
                listener.onItemClick(etAmount.text.toString().toInt()) // 수정하려는 수량 보내주기
                dismiss()
            }
        }
    }

}