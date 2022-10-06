package com.ssafy.birdmeal.view.loading

import com.ssafy.birdmeal.view.home.LoadingDialog

class LoadingFragmentDialog {
    companion object {
        val loadingPhotoCardDialog by lazy { LoadingDialog("포토카드 제작중...") }
        val loadingMintingDialog by lazy { LoadingDialog("NFT 받는중...") }
        val loadingFillUpDialog by lazy { LoadingDialog("토큰 충전중...") }
        val loadingLoginDialog by lazy { LoadingDialog("로그인중...") }
        val loadingDonationDialog by lazy { LoadingDialog("기부 중...") }
        val loadingOrderAssumeDialog by lazy { LoadingDialog("구매 확정중...") }
        val loadingOrderCancelDialog by lazy { LoadingDialog("구매 취소중...") }
        val loadingOrderRefundDialog by lazy { LoadingDialog("구매 환불중...") }
        val loadingOrderDialog by lazy { LoadingDialog("결제 진행중...") }
        val loadingWalletDialog by lazy { LoadingDialog("지갑 생성중...") }
    }
}