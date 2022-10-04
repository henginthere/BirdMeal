package com.ssafy.birdmeal.view.home

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import com.ssafy.birdmeal.R

class IntroWallet : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Hide/Show the status Bar
        showStatusBar(true)
        // Control the status bar color
        setStatusBarColorRes(R.color.white)

        setNextArrowColor(R.color.black_high_emphasis)
        setBackArrowColor(R.color.black_high_emphasis)
        setColorSkipButton(R.color.black_high_emphasis)
        setColorDoneText(R.color.black_high_emphasis)

        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.black_high_emphasis),
            unselectedIndicatorColor = getColor(R.color.black_low_emphasis)
        )

        // Control the navigation bar color
        setNavBarColorRes(R.color.white)

        // Enable vibration and set duration in ms
        isVibrate = true
        vibrateDuration = 50L

        isSystemBackButtonLocked = true

        setTransformer(AppIntroPageTransformerType.Zoom)

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        add(
            title = "디지털 지갑을 생성할 수 있습니다",
            description = "버드밀의 자체 토큰 \"엘레나\" 토큰을 소지하기 위해서는 이더리움 기반 디지털 지갑이 필요해요",
            R.drawable.info_wallet_1
        )
        add(
            title = "지갑이 없어도 괜찮아요 :)",
            description = "새롭게 디지털 지갑을 만들 수 있어요\n\n지갑에 설정할 비밀번호를 준비해주세요!",
            R.drawable.info_wallet_2
        )
        add(
            title = "혹시 개인키가 있으신가요?",
            description = "기존 개인키를 이용해서 지갑을 가져올 수 있습니다\n\n기존 개인키와 지갑에 설정할 비밀번호를 준비해주세요!",
            R.drawable.info_wallet_3
        )
        add(
            title = "개인키가 뭔가요?",
            description = "개인키를 이용해서 거래를 발생시킬 수 있어요!\n\n개인키로 암호화폐의 소유권을 증명하는 것이에요!\n\n따라서, 개인키가 노출되거나 분실하지 않도록 소중히 보관해야돼요!!!",
            R.drawable.info_wallet_4
        )
        add(
            title = "지갑 주소는 뭐죠?",
            description = "은행의 계좌 번호라고 생각하면 돼요!\n\n지갑 주소를 이용해서 토큰을 송금할 수 있어요!!",
            R.drawable.info_wallet_5
        )
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }

    fun add(title: String, description: String, @DrawableRes imageDrawable: Int) {
        addSlide(
            AppIntroFragment.createInstance(
                title = title,
                description = description,
                imageDrawable = imageDrawable,
                titleColorRes = R.color.black,
                descriptionColorRes = R.color.black_high_emphasis,
                backgroundColorRes = R.color.white,
                descriptionTypefaceFontRes = R.font.gong_gothic_light,
            )
        )
    }
}