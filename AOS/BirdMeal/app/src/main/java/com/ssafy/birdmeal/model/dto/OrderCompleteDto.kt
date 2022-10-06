package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class OrderCompleteDto(
    @SerializedName("productName") val productName: String,
    @SerializedName("productSize") val productSize: Int,
    @SerializedName("userAddress") val userAddress: String?,
    @SerializedName("orderDate") val orderDate: String,
    @SerializedName("totalAmount") val totalAmount: Int,
    @SerializedName("donationAmount") val donationAmount: Int,
)