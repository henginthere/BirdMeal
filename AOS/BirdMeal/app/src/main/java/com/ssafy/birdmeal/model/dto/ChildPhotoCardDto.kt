package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class ChildPhotoCardDto(
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("nftImg") val nftImg: String
)