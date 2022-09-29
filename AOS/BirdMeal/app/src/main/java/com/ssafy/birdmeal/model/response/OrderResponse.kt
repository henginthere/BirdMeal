package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class OrderResponse (

    @SerializedName("orderSeq") val orderSeq: Int,
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("orderDate") val orderDate: String,
    @SerializedName("orderCnt") val orderCnt: Int,
    @SerializedName("orderFirstName") val orderFirstName: String,
    @SerializedName("orderPrice") val orderPrice: Int,
    @SerializedName("productThumbnailImg") val productThumbnailImg: String

    )
