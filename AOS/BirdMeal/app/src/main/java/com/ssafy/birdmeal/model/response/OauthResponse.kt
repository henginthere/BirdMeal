package com.ssafy.birdmeal.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.birdmeal.model.dto.TokenDto

data class OauthResponse(
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("tokenDto") val tokenDto: TokenDto
)