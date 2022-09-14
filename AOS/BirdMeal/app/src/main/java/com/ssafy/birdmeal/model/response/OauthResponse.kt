package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.birdmeal.model.dto.UserDto

data class OauthResponse(
    @SerializedName("userDto") val userDto: UserDto,
    @SerializedName("token") val token: String,
    @SerializedName("userRole") val userRole: String
)