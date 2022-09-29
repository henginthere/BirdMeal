package com.ssafy.birdmeal.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.text.TextUtils
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ssafy.birdmeal.R
import java.text.DecimalFormat

// 다이얼로그 사이즈 조절
fun Context.dialogResize(dialog: Dialog, width: Float, height: Float) {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT < 30) {
        val display = windowManager.defaultDisplay
        val size = Point()

        display.getSize(size)

        val window = dialog.window

        val x = (size.x * width).toInt()
        val y = (size.y * height).toInt()

        window?.setLayout(x, y)
    } else {
        val rect = windowManager.currentWindowMetrics.bounds

        val window = dialog.window
        val x = (rect.width() * width).toInt()
        val y = (rect.height() * height).toInt()

        window?.setLayout(x, y)
    }
}

// 서버에서 이미지 받아오는 포맷
fun ImageView.imageFormatter(url: String) {
    Glide.with(this.context).load("$url")
        .placeholder(R.drawable.meal)
        .into(this)
}

// 지갑 path 가져오기
fun Context.getWalletPath() = run { "$filesDir/wallet" }

// EditText 유효성 검사
fun validity(et: EditText) = !TextUtils.isEmpty(et.text)

// 상태바 색 변경
fun changeStatusBarColor(activity: Activity, color: String) {
    if (Build.VERSION.SDK_INT >= 21) {
        val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        when (color) {
            WHITE -> {
                window.statusBarColor = activity.resources.getColor(R.color.white)
            }
            BEIGE -> {
                window.statusBarColor = activity.resources.getColor(R.color.back_beige)
            }
        }
    }
}

//화폐단위
fun getDecimalFormat(number: Long): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(number)
}

fun getDecimalFormat(number: Int): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(number)
}