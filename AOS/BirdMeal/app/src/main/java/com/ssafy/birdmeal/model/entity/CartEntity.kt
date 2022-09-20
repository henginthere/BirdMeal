package com.ssafy.birdmeal.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_cart")
data class CartEntity(
    @PrimaryKey val productSeq: Int,
    val productName: String,
    val productPrice: Int,
    val productCa: String? = null,
    val productThumbnailImg: String,
    val productDescriptionImg: String,
    val productCount: Int
)