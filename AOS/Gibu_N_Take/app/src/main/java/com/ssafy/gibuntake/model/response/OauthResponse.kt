package com.ssafy.gibuntake.model.response

import com.google.gson.annotations.SerializedName

data class OauthResponse(
    @SerializedName("msg") val msg: String = "",
    @SerializedName("isRegistered") val isRegistered: Boolean = false,
    @SerializedName("email") val email: String = "",
    @SerializedName("userSeq") val userSeq: Int = 0,
    @SerializedName("jwtToken") val jwtToken: String = ""
)