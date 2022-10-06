package com.ssafy.birdmeal.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "t_cart")
data class CartEntity(
    @PrimaryKey val productSeq: Int,
    val productName: String,
    val productPrice: Int,
    val productCa: String? = null,
    val productThumbnailImg: String,
    val productDescriptionImg: String,
    var productCount: Int
) : Parcelable
