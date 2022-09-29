package com.ssafy.birdmeal.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.ssafy.birdmeal.databinding.DialogYesOrNoBinding

class YesOrNoDialog(
    val title: String,
    val positive: String,
    val negative: String,
    val positiveClick: () -> Unit,
    val negativeClick: () -> Unit
) : DialogFragment() {

    var binding: DialogYesOrNoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogYesOrNoBinding.inflate(inflater, container, false)

        // 다이얼로그 뒤에 배경 흐리게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.title = this.title
        binding?.positive = this.positive
        binding?.negative = this.negative
        binding?.btnPositive?.setOnClickListener {
            positiveClick.invoke()
            dismiss()
        }
        binding?.btnNegative?.setOnClickListener {
            negativeClick.invoke()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}