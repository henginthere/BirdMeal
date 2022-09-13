package com.ssafy.birdmeal.view.login

import android.content.Intent
import android.content.SharedPreferences
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseActivity
import com.ssafy.birdmeal.databinding.ActivityLoginBinding
import com.ssafy.birdmeal.utils.JWT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun init() {
//        autoLogin()
    }

    private fun autoLogin(){ // 자동 로그인
        val jwt = sharedPref.getString(JWT, "")

        if(jwt != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}