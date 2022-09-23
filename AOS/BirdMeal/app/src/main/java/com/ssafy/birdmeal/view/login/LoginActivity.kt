package com.ssafy.birdmeal.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseActivity
import com.ssafy.birdmeal.databinding.ActivityLoginBinding
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.JWT
import com.ssafy.birdmeal.utils.WHITE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun init() {
//        autoLogin()
    }

    private fun autoLogin() { // 자동 로그인
        val jwt = sharedPref.getString(JWT, "")

        if (jwt != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    // 상태바 색 변경
    fun setStatusbarColor(color: String) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window: Window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
            when (color) {
                WHITE -> {
                    window.statusBarColor = this.resources.getColor(R.color.white)
                }
                BEIGE -> {
                    window.statusBarColor = this.resources.getColor(R.color.back_beige)
                }
            }
        }
    }
}

