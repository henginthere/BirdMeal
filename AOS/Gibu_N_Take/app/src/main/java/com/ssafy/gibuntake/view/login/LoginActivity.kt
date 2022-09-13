package com.ssafy.gibuntake.view.login

import android.content.Intent
import android.content.SharedPreferences
import com.ssafy.gibuntake.MainActivity
import com.ssafy.gibuntake.R
import com.ssafy.gibuntake.base.BaseActivity
import com.ssafy.gibuntake.databinding.ActivityLoginBinding
import com.ssafy.gibuntake.utils.JWT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun init() {
        autoLogin()
    }

    private fun autoLogin(){ // 자동 로그인
        val jwt = sharedPref.getString(JWT, "")

        if(jwt != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}