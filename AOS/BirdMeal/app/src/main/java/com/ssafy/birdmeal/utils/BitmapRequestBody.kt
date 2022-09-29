package com.ssafy.birdmeal.utils

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink

class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
    override fun contentType(): MediaType? {
        return "image/*".toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        bitmap.compress(Bitmap.CompressFormat.PNG, 99, sink.outputStream()) //99프로 압축
    }
}