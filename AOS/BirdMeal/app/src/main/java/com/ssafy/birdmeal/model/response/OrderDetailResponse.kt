package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class OrderDetailResponse (

    @SerializedName("orderDetailSeq") val orderDetailSeq: Int,
    @SerializedName("productName") val productName: String,
    @SerializedName("productPrice") val productPrice: Int,
    @SerializedName("productQuantity") val productQuantity: Int,
    @SerializedName("orderDate") val orderDate: String,
    @SerializedName("orderToState") val orderToState: Boolean,
    @SerializedName("OrderDeliveryNumber") val OrderDeliveryNumber: String,
    @SerializedName("OrderDeliveryCompany") val OrderDeliveryCompany: String,
    @SerializedName("productThumbnailImg") val productThumbnailImg: String

    )