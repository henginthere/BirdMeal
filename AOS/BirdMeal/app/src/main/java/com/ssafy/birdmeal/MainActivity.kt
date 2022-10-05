package com.ssafy.birdmeal

import android.content.SharedPreferences
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.awesomedialog.*
import com.ssafy.birdmeal.base.BaseActivity
import com.ssafy.birdmeal.databinding.ActivityMainBinding
import com.ssafy.birdmeal.di.ApplicationClass.Companion.PACKAGE_NAME
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.view.donation.DonationViewModel
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingAssumeDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingDonationDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingFillUpDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingMintingDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderDialog
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import com.ssafy.birdmeal.view.my_page.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController

    private val donationViewModel by viewModels<DonationViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private val orderViewModel by viewModels<OrderViewModel>()
    private val nftViewModel by viewModels<NFTViewModel>()
    private val shoppingViewModel by viewModels<ShoppingViewModel>()

    override fun init() {
        PACKAGE_NAME = application.packageName

        initNavigation()

        initViewModelCallBack()
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
            지갑생성, 기부하기, 포토카드, MY NFT, 주소 검색, 결제
            화면에서 바텀 네비 미표시
             */
            if (destination.id == R.id.createWalletFragment ||
                destination.id == R.id.donateFragment || destination.id == R.id.canvasFragment ||
                destination.id == R.id.myNftListFragment || destination.id == R.id.searchAddressFragment ||
                destination.id == R.id.orderFragment
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

    private fun initViewModelCallBack() {
        donationViewModel.contractErrMsgEvent.observe(this) {
            when (it) {
                ERR_GET_DONATION_AMOUNT -> {}
                ERR_DO_DONATE -> loadingDonationDialog.dismiss()
            }
            contractErrdialog(it)
        }
        userViewModel.contractErrMsgEvent.observe(this) {
            when (it) {
                ERR_GET_USER_TOKEN -> {}
                ERR_FILLUP_TOKEN -> loadingFillUpDialog.dismiss()
                ERR_FILLUP_TOKEN_CHILD -> loadingFillUpDialog.dismiss()
            }
            contractErrdialog(it)
        }
        orderViewModel.contractErrMsgEvent.observe(this) {
            when (it) {
                ERR_UPDATE_ORDER_STATE -> loadingAssumeDialog.dismiss()
            }
            contractErrdialog(it)
        }
        nftViewModel.contractErrMsgEvent.observe(this) {
            when (it) {
                ERR_DO_MINTING -> loadingMintingDialog.dismiss()
                ERR_GET_MY_NFT -> {}
            }
            contractErrdialog(it)
        }
        shoppingViewModel.contractErrMsgEvent.observe(this) {
            loadingOrderDialog.dismiss()
            contractErrdialog(it)
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

    // 블록체인 통신 오류 다이얼로그
    fun contractErrdialog(msg: String) {
        AwesomeDialog.build(this)
            .title("통신 오류")
            .body("블록체인 네트워크 통신에\n오류가 발생했습니다\n다시 시도해주세요\n$msg")
            .icon(R.drawable.ic_warn)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_main_color) {

            }
    }
}