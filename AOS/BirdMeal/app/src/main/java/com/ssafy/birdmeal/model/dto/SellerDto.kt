package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class SellerDto(
    @SerializedName("sellerAddress") val sellerAddress: String,
    @SerializedName("sellerCreateDate") val sellerCreateDate: String,
    @SerializedName("sellerEmail") val sellerEmail: String,
    @SerializedName("sellerEoa") val sellerEoa: String,
    @SerializedName("sellerInfo") val sellerInfo: Any? = null,
    @SerializedName("sellerNickname") val sellerNickname: String,
    @SerializedName("sellerSeq") val sellerSeq: Int,
    @SerializedName("sellerTel") val sellerTel: String,
    @SerializedName("sellerUpdateDate") val sellerUpdateDate: String
)