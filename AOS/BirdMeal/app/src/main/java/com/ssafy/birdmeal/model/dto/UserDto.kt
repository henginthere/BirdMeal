package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("userEmail") val userEmail: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userEoa") val userEoa: String?,
    @SerializedName("userTel") var userTel: String?,
    @SerializedName("userAdd") var userAdd: String?,
    @SerializedName("userChargeState") val userChargeState: Boolean,
    @SerializedName("userRole") val userRole: Boolean, // false: 일반 사용자
    @SerializedName("userIsMint") val userIsMint: Boolean,
    @SerializedName("userMonthMoney") val userMonthMoney: Int
)