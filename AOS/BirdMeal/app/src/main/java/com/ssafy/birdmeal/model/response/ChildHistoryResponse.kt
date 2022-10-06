package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class ChildHistoryResponse(
    @SerializedName("orderChildDetailSeq") val orderChildDetailSeq: Int,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("orderDate") val orderDate: String,
    @SerializedName("orderQuantity") val orderQuantity: Int,
    @SerializedName("productName") val productName: String,
    @SerializedName("productPrice") val productPrice: Int,
    @SerializedName("productThumbnailImg") val productThumbnailImg: String
)