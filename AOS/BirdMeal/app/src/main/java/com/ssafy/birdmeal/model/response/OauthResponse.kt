package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName

data class OauthResponse(
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("token") val token: String
)