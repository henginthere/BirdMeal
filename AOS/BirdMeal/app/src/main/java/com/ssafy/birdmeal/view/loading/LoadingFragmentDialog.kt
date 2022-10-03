package com.ssafy.birdmeal.view.loading

import com.ssafy.birdmeal.view.home.LoadingDialog

class LoadingFragmentDialog {
    companion object {
        val loadingPhotoCardDialog by lazy { LoadingDialog("포토카드 제작중...") }
        val loadingMintingDialog by lazy { LoadingDialog("NFT 받는중...") }
        val loadingFillUpDialog by lazy { LoadingDialog("토큰 충전중...") }
        val loadingLoginDialog by lazy { LoadingDialog("로그인중...") }
        val loadingDonationDialog by lazy { LoadingDialog("기부중...") }
        val loadingAssumeDialog by lazy { LoadingDialog("구매 확정중...") }
    }
}