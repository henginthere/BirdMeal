package com.ssafy.birdmeal.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<out T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T,
    @SerializedName("msg") val msg: String
)