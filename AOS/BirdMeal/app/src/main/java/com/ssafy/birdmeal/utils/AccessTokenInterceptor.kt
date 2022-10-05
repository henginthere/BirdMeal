package com.ssafy.birdmeal.utils

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val sharedPref: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var token = runBlocking {
            sharedPref.getString(JWT, "")!!
        }

        val request = chain.request().newBuilder()
            .addHeader(JWT, "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}