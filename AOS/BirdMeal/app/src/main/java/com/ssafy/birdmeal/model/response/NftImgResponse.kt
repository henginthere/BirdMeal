package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class NftImgResponse(
    @SerializedName("nftSeq") val nftSeq: Int,
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("nftImg") val nftImg: String
)