package com.ssafy.birdmeal

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.awesomedialog.*
import com.ssafy.birdmeal.base.BaseActivity
import com.ssafy.birdmeal.databinding.ActivityMainBinding
import com.ssafy.birdmeal.di.ApplicationClass.Companion.PACKAGE_NAME
import com.ssafy.birdmeal.utils.FILL_COMPLETED
import com.ssafy.birdmeal.utils.FILL_ERR
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.donation.DonationViewModel
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingDonationDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingFillUpDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingLoginDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingMintingDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderAssumeDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderCancelDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderRefundDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingPhotoCardDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingWalletDialog
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import com.ssafy.birdmeal.view.my_page.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var sharedPref: SharedPreferences
    private lateinit var navController: NavController

    // NFC ForegroundMode
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>
    private lateinit var nfcAdapter: NfcAdapter

    private val donationViewModel by viewModels<DonationViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private val orderViewModel by viewModels<OrderViewModel>()
    private val nftViewModel by viewModels<NFTViewModel>()
    private val shoppingViewModel by viewModels<ShoppingViewModel>()

    override fun init() {
        PACKAGE_NAME = application.packageName

        initNfc()

        initNavigation()

        initViewModelCallBack()
    }

    // nfc 포그라운드
    private fun initNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val filter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        filters = arrayOf(filter)
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, pIntent, filters, null)
    }

    // NFC TAG 인식
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val action = intent!!.action
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_TAG_DISCOVERED) ||
            action.equals(NfcAdapter.ACTION_TECH_DISCOVERED)
        ) {
            showToast("충전 쿠폰 NFC가 인식되었습니다.")
            getNdefMessages(intent)
        }
    }

    private fun getNdefMessages(intent: Intent) {
        // 1. 인텐트에서 NdefMessage 배열 데이터를 가져온다
        var messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        // 2. Data 변환
        if (messages != null) {
            val msgArr = arrayOfNulls<NdefMessage>(messages.size)

            for (i in msgArr.indices) {
                msgArr[i] = messages[i] as NdefMessage?
            }

            // 3. NdefMessage 에서 NdefRecode -> payload 가져오기, 여러개인 경우 for 문 사용
            val payload = msgArr[0]!!.records[0].payload
            var result = String(payload).substring(3) // 앞에 en 빼주기

            var price = result!!.toInt()
            Log.d(TAG, "getNdefMessages 들어온 금액: $price")

            userViewModel.fillUpToken(price)
            loadingFillUpDialog.show(supportFragmentManager, "loadingFillUpDialog")
        }
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
            지갑생성, 기부하기, 포토카드, MY NFT, 주소 검색, 결제, nft자세히보기
            화면에서 바텀 네비 미표시
             */
            if (destination.id == R.id.createWalletFragment ||
                destination.id == R.id.donateFragment || destination.id == R.id.canvasFragment ||
                destination.id == R.id.myNftListFragment || destination.id == R.id.searchAddressFragment ||
                destination.id == R.id.orderFragment || destination.id == R.id.nftDetailFragment
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
        donationViewModel.apply {
            errMsgEvent.observe(this@MainActivity) {
                serverErrDialog(it)
            }
            contractErrMsgEvent.observe(this@MainActivity) {
                contractErrDialog(it)
            }
        }
        userViewModel.apply {
            errMsgEvent.observe(this@MainActivity) {
                serverErrDialog(it)
            }
            contractErrMsgEvent.observe(this@MainActivity) {
                contractErrDialog(it)
            }
            tokenMsgEvent.observe(this@MainActivity) {
                when (it) {
                    FILL_COMPLETED -> {
                        loadingFillUpDialog.dismiss()
                        completedFillUpDialog()
                    }
                    // 컨트랙트 통신 오류
                    FILL_ERR -> {
                        loadingFillUpDialog.dismiss()
                    }
                }
            }
        }
        orderViewModel.apply {
            errMsgEvent.observe(this@MainActivity) {
                serverErrDialog(it)
            }
            contractErrMsgEvent.observe(this@MainActivity) {
                contractErrDialog(it)
            }
        }
        nftViewModel.apply {
            errMsgEvent.observe(this@MainActivity) {
                serverErrDialog(it)
            }
            contractErrMsgEvent.observe(this@MainActivity) {
                contractErrDialog(it)
            }
        }
        shoppingViewModel.apply {
            errMsgEvent.observe(this@MainActivity) {
                serverErrDialog(it)
            }
            contractErrMsgEvent.observe(this@MainActivity) {
                contractErrDialog(it)
            }
        }
    }

    private var waitTime = 0L
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
    private fun contractErrDialog(msg: String) {
        loadingDismiss()

        AwesomeDialog.build(this)
            .title("통신 오류")
            .body("블록체인 네트워크 통신에\n오류가 발생했습니다\n다시 시도해주세요\n$msg")
            .icon(R.drawable.ic_warn)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_main_color) {}
    }

    private fun serverErrDialog(msg: String) {
        loadingDismiss()

        AwesomeDialog.build(this)
            .title("통신 오류")
            .body("오류가 발생했습니다\n다시 시도해주세요\n$msg")
            .icon(R.drawable.ic_warn)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_main_color) {}
    }

    private fun loadingDismiss() {
        if (loadingPhotoCardDialog.isAdded) loadingPhotoCardDialog.dismiss()
        if (loadingMintingDialog.isAdded) loadingMintingDialog.dismiss()
        if (loadingFillUpDialog.isAdded) loadingFillUpDialog.dismiss()
        if (loadingLoginDialog.isAdded) loadingLoginDialog.dismiss()
        if (loadingDonationDialog.isAdded) loadingDonationDialog.dismiss()
        if (loadingOrderAssumeDialog.isAdded) loadingOrderAssumeDialog.dismiss()
        if (loadingOrderCancelDialog.isAdded) loadingOrderCancelDialog.dismiss()
        if (loadingOrderRefundDialog.isAdded) loadingOrderRefundDialog.dismiss()
        if (loadingOrderDialog.isAdded) loadingOrderDialog.dismiss()
        if (loadingWalletDialog.isAdded) loadingWalletDialog.dismiss()
    }

    // 충전 완료 다이얼로그(아동)
    private fun completedFillUpDialog() {
        AwesomeDialog.build(this)
            .title("충전 완료")
            .body("충전이 완료되었습니다")
            .icon(R.drawable.ic_duck)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {}
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }
}