package com.ssafy.birdmeal.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mikhaellopez.rxanimation.RxAnimation
import com.mikhaellopez.rxanimation.scale
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.databinding.DialogLoadingBinding

class LoadingDialog(val title: String) : DialogFragment() {

    var binding: DialogLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_loading, container, false)

        // 취소 불가능
        setCancelable(false)

        // 배경 투명하게 바꿔줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.title = this.title

        // 구매확정과 결제 로딩에만 설명 텍스트 보여주기
        val str = this.title.split(" ")[0]
        if(str == "결제" || str == "구매" || str == "기부"){
            binding?.tvDescription!!.visibility = View.VISIBLE
        }

        initAnimation()
    }

    private fun initAnimation() {
        RxAnimation.from(binding!!.tvLoading)
            .scale(1.1f, duration = 500L, reverse = true)
            .repeat()
            .subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}