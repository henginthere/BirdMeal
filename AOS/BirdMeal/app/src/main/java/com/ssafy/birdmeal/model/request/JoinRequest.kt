package com.ssafy.birdmeal.model.request

data class JoinRequest(
    val userEmail: String,
    val userNickname: String,
    val userRole: String
)