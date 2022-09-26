package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class OrderDetailResponse (

    @SerializedName("orderDetailSeq") val orderDetailSeq: Int,
    @SerializedName("productName") val productName: String,
    @SerializedName("productPrice") val productPrice: Int,
    @SerializedName("orderQuantity") val orderQuantity: Int,
    @SerializedName("orderDate") val orderDate: String,
    @SerializedName("orderToState") val orderToState: Boolean,
    @SerializedName("orderDeliveryNumber") val orderDeliveryNumber: String,
    @SerializedName("orderDeliveryCompany") val orderDeliveryCompany: String,
    @SerializedName("productThumbnailImg") val productThumbnailImg: String

    )