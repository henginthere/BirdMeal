package com.ssafy.birdmeal.view.loading

import com.ssafy.birdmeal.view.home.LoadingDialog

class LoadingFragmentDialog {
    companion object {
        val loadingPhotoCardDialog by lazy { LoadingDialog("포토카드 제작중...") }
        val loadingMintingDialog by lazy { LoadingDialog("NFT 받는중...") }
    }
}