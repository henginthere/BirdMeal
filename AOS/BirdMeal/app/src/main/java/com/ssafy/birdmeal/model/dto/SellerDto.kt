package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class SellerDto(
    @SerializedName("sellerSeq") val sellerSeq: Int,
    @SerializedName("sellerImg") val sellerImg: String,
    @SerializedName("sellerWallet") val sellerWallet: String,
    @SerializedName("sellerAddress") val sellerAddress: String,
    @SerializedName("sellerEmail") val sellerEmail: String,
    @SerializedName("sellerEoa") val sellerEoa: String,
    @SerializedName("sellerInfo") val sellerInfo: String? = "",
    @SerializedName("sellerNickname") val sellerNickname: String,
    @SerializedName("sellerTel") val sellerTel: String,
    @SerializedName("sellerCreateDate") val sellerCreateDate: String,
    @SerializedName("sellerUpdateDate") val sellerUpdateDate: String
)