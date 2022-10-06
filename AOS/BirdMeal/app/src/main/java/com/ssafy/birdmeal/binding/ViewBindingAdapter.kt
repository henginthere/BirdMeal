package com.ssafy.birdmeal.binding

import android.graphics.Color
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
                .override(R.dimen.cartImg * 2, R.dimen.cartImg * 2)
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
                .override(R.dimen.thumbnailImgWidth * 2, R.dimen.thumbnailImgHeight * 2)
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
                .override(R.dimen.thumbnailImgWidth * 2, R.dimen.descriptionImgHeight * 2)
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
        if (size > 2) {
            text += " 외 ${size - 1}건"
        }
        this.text = text
    }

    @BindingAdapter("orderHistoryName", "orderHistorySize")
    @JvmStatic
    fun TextView.setOrderHistoryName(name: String, size: Int) {
        var text = "$name"
        if (size > 0) {
            text += " 외 ${size}건"
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
        if (productCnt > 0) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.INVISIBLE
        }
    }

    // 장바구니 비었습니다 텍스트 표시
    @BindingAdapter("textEmpty")
    @JvmStatic
    fun TextView.setTextEmpty(productCnt: Int) {
        if (productCnt > 0) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }

    // NFT 이미지
    @BindingAdapter("nftImg")
    @JvmStatic
    fun ImageView.setNftImg(url: String?) {
        if (url != null) {
            Glide.with(this.context)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(this)
        }
    }

    // NFT 받기 텍스트 색
    @BindingAdapter("setGetNftButton")
    @JvmStatic
    fun ImageView.setGetNftButton(isMint: Boolean?) {
        if (isMint != null) {
            if (isMint) {
                this.background = resources.getDrawable(R.drawable.btn_get_nft)
            } else {
                this.background = resources.getDrawable(R.drawable.btn_get_nft_false)
            }
        }
    }

    // 이번달 내 기부금액 텍스트
    @BindingAdapter("myDonationAmount")
    @JvmStatic
    fun TextView.myDonationAmount(productCnt: Int) {
        if (productCnt > 0) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }

    // 토큰 충전 버튼 색 (아동)
    @BindingAdapter("setFillUpButton")
    @JvmStatic
    fun ImageView.setFillUpButton(userChargeState: Boolean?) {
        Log.d(TAG, "setFillUpButton: $userChargeState")
        if (userChargeState != null) {
            if (userChargeState) {
                this.setImageResource(R.drawable.btn_charge)
            } else {
                this.setImageResource(R.drawable.btn_charged)
            }
        }
    }

    // 유저 토큰 NULL 처리
    @BindingAdapter("myELN")
    @JvmStatic
    fun TextView.myELN(userELN: String?) {
        if (userELN == null) {
            this.text = "0 ELN"
        } else {
            this.text = "$userELN ELN"
        }
    }

    // 유저 토큰 NULL 처리
    @BindingAdapter("myNFT")
    @JvmStatic
    fun TextView.myNFT(count: String?) {
        if (count == null) {
            this.text = "보유 NFT : 0개"
        } else {
            this.text = "보유 NFT : ${count}개"
        }
    }
}