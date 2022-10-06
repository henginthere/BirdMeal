package com.ssafy.birdmeal.model.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("categorySeq") val categorySeq: Int,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("categoryIcon") val categoryIcon: String
)