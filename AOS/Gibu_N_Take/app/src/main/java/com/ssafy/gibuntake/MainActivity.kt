package com.ssafy.gibuntake

import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.gibuntake.base.BaseActivity
import com.ssafy.gibuntake.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController

    override fun init() {
        initNavigation()
    }

    private fun initNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        navController = navHostFragment.navController

        // 바텀 네비게이션 보이는 화면 구분
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.HomeFragment){

            }
            else {

            }
        }
    }

    // 홈 화면에서 뒤로가기 2번 클릭 시 앱 종료
    var waitTime = 0L
    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.HomeFragment){
            if(System.currentTimeMillis() - waitTime >= 1500){
                waitTime = System.currentTimeMillis()
                showToast("뒤로가기 버튼을 누르면 종료됩니다.")
            }
            else {
                finish()
            }
        }
        else {
            super.onBackPressed()
        }
    }

}