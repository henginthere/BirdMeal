package com.ssafy.birdmeal.binding

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.birdmeal.R

object ViewBindingAdapter {

    @BindingAdapter("categoryImg")
    @JvmStatic
    fun AppCompatImageView.setCategoryImg(imgUrl: String){
        Glide.with(this.context)
            .load(R.drawable.meal)
            .override(R.dimen.categoryImg*2, R.dimen.categoryImg*2)
            .placeholder(R.drawable.meal)
            .into(this)
        // this.clipToOutline = true 배경이 Drawable 파일이면 그걸 배경으로 인식
    }
    
    @BindingAdapter("productPrice")
    @JvmStatic
    fun TextView.setProductPrice(price: Int){
        text = price.toString() + '원'
    }

}