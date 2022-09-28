package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("productSeq") val productSeq: Int,
    @SerializedName("categorySeq") val categorySeq: Int,
    @SerializedName("sellerSeq") val sellerSeq: Int,
    @SerializedName("sellerName") val sellerName: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("productPrice") val productPrice: Int,
    @SerializedName("productCa") val productCa: String? = null,
    @SerializedName("productThumbnailImg") val productThumbnailImg: String,
    @SerializedName("productDescriptionImg") val productDescriptionImg: String,
    @SerializedName("productIsDeleted") val productIsDeleted: Boolean = false,
    @SerializedName("productCreateDate") val productCreateDate: String? = null,
    @SerializedName("productUpdateDate") val productUpdateDate: String? = null
)