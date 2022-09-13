package com.ssafy.birdmeal.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide

// 다이얼로그 사이즈 조절
fun Context.dialogResize(dialog: Dialog, width: Float, height: Float){
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if(Build.VERSION.SDK_INT < 30){
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
fun ImageView.imageFormatter(imageSeq: Int){
    Glide.with(this.context).load("${BASE_URL}images/${imageSeq}")
        //.placeholder(R.drawable.img)
        .into(this)
}
