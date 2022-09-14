package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("categorySeq") val categorySeq: Int,
    @SerializedName("productCa") val productCa: String,
    @SerializedName("productCreateDate") val productCreateDate: String,
    @SerializedName("productDescriptionImg") val productDescriptionImg: String,
    @SerializedName("productIsDeleted") val productIsDeleted: Boolean,
    @SerializedName("productName") val productName: String,
    @SerializedName("productPrice") val productPrice: Int,
    @SerializedName("productSeq") val productSeq: Int,
    @SerializedName("productThumbnailImg") val productThumbnailImg: String,
    @SerializedName("productUpdateDate") val productUpdateDate: String,
    @SerializedName("sellerSeq") val sellerSeq: Int
)