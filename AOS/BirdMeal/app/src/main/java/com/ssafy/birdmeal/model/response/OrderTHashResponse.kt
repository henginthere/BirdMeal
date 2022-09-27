package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class OrderTHashResponse (
    @SerializedName("orderDetailSeq") val orderDetailSeq: Int,
    @SerializedName("orderTHash") val orderTHash: String,
    @SerializedName("productPrice") val productPrice: Int,
    @SerializedName("orderQuantity") val orderQuantity: Int,
    @SerializedName("productCa") val productCa: String

)
