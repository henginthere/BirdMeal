package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("userEmail") val userEmail: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userEoa") val userEoa: String?,
    @SerializedName("userTel") val userTel: String?,
    @SerializedName("userAdd") val userAdd: String?,
    @SerializedName("userChargeState") val userChargeState: Boolean,
)