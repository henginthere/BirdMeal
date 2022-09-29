package com.ssafy.birdmeal.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.di.ApplicationClass.Companion.PACKAGE_NAME
import com.ssafy.birdmeal.utils.TAG


object ViewBindingAdapter {

    @BindingAdapter("categoryImg")
    @JvmStatic
    fun AppCompatImageView.setCategoryImg(imgUrl: String) {
        val url = "drawable/$imgUrl"
        val imageResource = resources.getIdentifier(url, null, PACKAGE_NAME)
        val image = resources.getDrawable(imageResource)

        this.setImageDrawable(image)
    }

    @BindingAdapter("productPrice")
    @JvmStatic
    fun TextView.setProductPrice(price: Int) {
        var t = String.format("%,2d", price)
        text = "$t ELN"
    }

    @BindingAdapter("cartThumbnail")
    @JvmStatic
    fun ImageView.setCartThumbnail(url: String?) {
        if (url != null) {
            Glide.with(this.context)
                .load("$url")
                .override(R.dimen.cartImg*2, R.dimen.cartImg*2)
                .placeholder(com.ssafy.birdmeal.R.drawable.meal)
                .into(this)
        }
    }

    @BindingAdapter("productThumbnail")
    @JvmStatic
    fun ImageView.setProductThumbnail(url: String?) {
        if (url != null) {
            Glide.with(this.context)
                .load("$url")
                .override(R.dimen.thumbnailImgWidth*2, R.dimen.thumbnailImgHeight*2)
                .placeholder(com.ssafy.birdmeal.R.drawable.meal)
                .into(this)
        }
    }

    @BindingAdapter("productDescription")
    @JvmStatic
    fun ImageView.setProductDescription(url: String?) {
        if (url != null) {
            Glide.with(this.context)
                .load("$url")
                .override(R.dimen.thumbnailImgWidth*2, R.dimen.descriptionImgHeight*2)
                .placeholder(R.drawable.meal)
                .into(this)
        }
    }


    @BindingAdapter("totalAmount", "userELN")
    @JvmStatic
    fun TextView.setRemainAmount(totalAmount: Int, userELN: Int) {
        var value = (userELN - totalAmount)
        this.text = String.format("%,2d", value) + " ELN"
    }

    @BindingAdapter("orderName", "orderSize")
    @JvmStatic
    fun TextView.setOrderName(name: String, size: Int) {
        var text = "$name"
        if (size > 1) {
            text += " 외 ${size - 1}건"
        }
        this.text = text
    }

    // 직접기부, 간접기부 이미지 분기
    @BindingAdapter("donationImg")
    @JvmStatic
    fun ImageView.setDonationImg(donationType: Boolean?) {
        if (donationType != null) {
            when (donationType) {
                true -> {
                    Glide.with(this.context)
                        .load(com.ssafy.birdmeal.R.drawable.ic_donation1)
                        .placeholder(com.ssafy.birdmeal.R.drawable.meal)
                        .into(this)
                }
                false -> {
                    Glide.with(this.context)
                        .load(com.ssafy.birdmeal.R.drawable.ic_donation2)
                        .placeholder(com.ssafy.birdmeal.R.drawable.meal)
                        .into(this)
                }
            }
        }
    }

    // 장바구니 New 아이콘 표시 or 제거
    @BindingAdapter("cartNewIcon")
    @JvmStatic
    fun ImageView.setNewIcon(productCnt: Int) {
        Log.d(TAG, "setNewIcon: 들어왔나요 $productCnt")
        if(productCnt > 0){
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.INVISIBLE
        }
    }

    // 장바구니 비었습니다 텍스트 표시
    @BindingAdapter("textEmpty")
    @JvmStatic
    fun TextView.setTextEmpty(productCnt: Int) {
        if(productCnt > 0){
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }
}