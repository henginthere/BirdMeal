package com.ssafy.birdmeal

import android.content.SharedPreferences
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ssafy.birdmeal.base.BaseActivity
import com.ssafy.birdmeal.databinding.ActivityMainBinding
import com.ssafy.birdmeal.di.ApplicationClass.Companion.PACKAGE_NAME
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController

    override fun init() {
        PACKAGE_NAME = application.packageName

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.apply {
            setupWithNavController(navController)
            background = null
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            /*
            홈, 지갑생성, 기부하기, 포토카드, MY NFT
            화면에서 바텀 네비 미표시
             */
            if (destination.id == R.id.homeFragment || destination.id == R.id.createWalletFragment ||
                destination.id == R.id.donateFragment || destination.id == R.id.canvasFragment ||
                destination.id == R.id.myNftListFragment
            ) {
                if (binding.bottomNav.visibility == View.VISIBLE) {
                    binding.bottomNav.visibility = View.GONE
                }
            } else { // 홈 화면 벗어나면 바텀 네비 표시
                if (binding.bottomNav.visibility == View.GONE) {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }

    var waitTime = 0L
    override fun onBackPressed() { // 기부 화면에서 뒤로가기 2번 클릭 시 앱 종료
        if (navController.currentDestination?.id == R.id.donationFragment) {
            if (System.currentTimeMillis() - waitTime >= 1500) {
                waitTime = System.currentTimeMillis()
                showToast("뒤로가기 버튼을 누르면 종료됩니다.")
            } else {
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }

}