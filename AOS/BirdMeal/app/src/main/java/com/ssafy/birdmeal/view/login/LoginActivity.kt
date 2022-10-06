package com.ssafy.birdmeal.view.login

import android.content.SharedPreferences
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseActivity
import com.ssafy.birdmeal.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun init() {
    }
}

