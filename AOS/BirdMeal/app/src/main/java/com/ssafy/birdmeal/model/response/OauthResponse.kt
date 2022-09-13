package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.birdmeal.model.dto.UserDto

data class OauthResponse(
    @SerializedName("data") val data: OauthResponseData?,
    @SerializedName("msg") val msg: String = "",
    @SerializedName("success") val success: Boolean = false
)

data class OauthResponseData(
    val userDto: UserDto?,
    val token: String = "",
    val userRole: String? = ""
)