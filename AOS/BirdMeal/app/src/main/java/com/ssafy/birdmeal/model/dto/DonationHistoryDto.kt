package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class DonationHistoryDto(
    @SerializedName("donationSeq") val donationSeq: Int = 0,
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("userNickname") val userNickname: String = "",
    @SerializedName("donationDate") val donationDate: String = "",
    @SerializedName("donationPrice") val donationPrice: Long,
    @SerializedName("donationType") val donationType: Boolean,
)