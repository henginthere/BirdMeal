package com.ssafy.birdmeal.view.my_page

import android.util.Log
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.*
import com.google.android.material.chip.Chip
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyPageBinding
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.view.donation.nft.CompletedMintingDialog
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingFillUpDialog
import com.ssafy.birdmeal.view.my_page.history.donation.MyDonationHistoryFragment
import com.ssafy.birdmeal.view.my_page.history.order.MyOrderHistoryFragment
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.Payload

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val nftViewModel by activityViewModels<NFTViewModel>()

    private lateinit var myOrderHistoryFragment: MyOrderHistoryFragment
    lateinit var myDonationHistoryFragment: MyDonationHistoryFragment

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        userViewModel.apply {
            getUserTokenValue()
            getUserInfo()

            if (user.value?.userRole!!) {
                binding.btnMyNft.setImageResource(R.drawable.btn_mind)
            }
        }
        binding.userVM = userViewModel

        initViewModelCallBack()
        initClickListener()
        initFragmentManager()
        initChip()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            successMsgEvent.observe(viewLifecycleOwner) {
//                showToast(it)
            }

            tokenChildMsgEvent.observe(viewLifecycleOwner) {
                when (it) {
                    // 이미 이번주에 충전한 경우
                    FILL_ALREADY -> {
                        alreadyFillUpDialog()
                    }
                    // 보유 토큰이 10만 이상인 경우
                    FILL_OVER -> {
                        overFillUpDialog()
                    }
                    // 충전이 가능한 경우
                    FILL_POSSIBLE -> {
                        confirmFillUpDialog()
                    }
                    // 충전 완료된 경우
                    FILL_COMPLETED -> {
                        completedFillUpDialog()
                    }
                    // 컨트랙트 통신 오류
                    FILL_ERR -> {
                        loadingFillUpDialog.dismiss()
                    }
                }
            }

            tokenChildLoadingEvent.observe(viewLifecycleOwner) {
                when (it) {
                    true -> loadingFillUpDialog.show(childFragmentManager, "loadingFillUpDialog")
                    false -> loadingFillUpDialog.dismiss()
                }
            }

            // 일반인 충전이 완료된 경우
            tokenMsgEvent.observe(viewLifecycleOwner) {
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

            userELN.observe(viewLifecycleOwner) {
                binding.tvEln.text = getDecimalFormat(it) + "  ELN"
            }
        }

        nftViewModel.apply {
            mintingMsgEvent.observe(viewLifecycleOwner) {
                LoadingFragmentDialog.loadingMintingDialog.dismiss()
                when (it) {
                    // 컨트랙트 통신에 실패한 경우
                    "" -> {}
                    // 통신 성공한 경우
                    else -> {
                        userViewModel.getUserInfo()
                        CompletedMintingDialog(it).show(
                            childFragmentManager,
                            "CompletedMintingDialog"
                        )
                    }
                }

            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 회원정보 수정
        btnModifyUser.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_editProfileFragment)
        }

        // 충전하기 버튼 클릭
        btnFillUpMoney.setOnClickListener {
            // 아동인 경우
            if (userViewModel.user.value?.userRole!!) {
                userViewModel.checkFillUpTokenChild()
            }
            // 일반인인 경우
            else {
                val dialog = FillUpMoneyDialog(listener)
                dialog.show(childFragmentManager, "FillUpMoneyDialog")
            }
        }

        // my nft 보기
        btnMyNft.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myNftListFragment)
        }

        // NFT 받기
        btnGetNft.setOnClickListener {
            if (userViewModel.user.value?.userIsMint!!) {
                mintingDialog()
            } else {
                noMintingDialog()
            }
        }
    }

    // 토큰 충전 다이얼로그 리스너
    private val listener = object : FillUpMoneyListener {
        override fun onItemClick(requestMoney: Int) { // 결제 API 호출
            // userViewModel.fillUpToken(requestMoney)
            // loadingFillUpDialog.show(childFragmentManager, "loadingFillUpDialog")
            payRequest(requestMoney)
        }
    }

    // 결제 API 호출
    private fun payRequest(requestMoney: Int) {
        val payload = Payload().apply {
            applicationId = PAY_APPLICATION_ID
            orderName = "BirdMeal 엘레나 토큰 충전"
            price = requestMoney.toDouble()
            orderId = "1"
        }

        Bootpay.init(childFragmentManager, requireContext())
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onDone(data: String?) { // 결제가 완료된 경우
                    Log.d(TAG, "onDone: $data")
                    userViewModel.fillUpToken(requestMoney)
                    loadingFillUpDialog.show(childFragmentManager, "loadingFillUpDialog")
                }

                override fun onConfirm(data: String?): Boolean {
                    return true
                }

                override fun onClose() {
                    Bootpay.removePaymentWindow()
                }

                override fun onCancel(data: String?) {
                    Log.d(TAG, "onCancel: $data")
                }

                override fun onIssued(data: String?) {} // 가상계좌 설정에 사용됨
                override fun onError(data: String?) { // 계좌에 예금이 부족한 경우 등 에러 처리
                    Log.d(TAG, "onError: $data")
                    val message = data!!.split(",")
                    val value = message[3].split(":")
                    showToast(value[1])
                }
            }).requestPayment()
    }

    private fun initFragmentManager() = with(binding) {

        myOrderHistoryFragment = MyOrderHistoryFragment()
        myDonationHistoryFragment = MyDonationHistoryFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.container_view, myDonationHistoryFragment)
            .commit()
    }

    private fun initChip() = with(binding) {
        chipMenuGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val chip = chipMenuGroup.children.find { (it as Chip).isChecked }
            val tag = (chip as Chip).tag

            when (tag) {
                "1" -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_view, myDonationHistoryFragment)
                        .commit()
                }
                "2" -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_view, myOrderHistoryFragment)
                        .commit()
                }
            }
        }
    }

    // 민팅하라는 다이얼로그
    private fun mintingDialog() {
        AwesomeDialog.build(requireActivity())
            .title("따뜻한 마음 감사합니다")
            .body("아이들이 제작한 포토카드 NFT를 받을 수 있습니다")
            .icon(R.drawable.ic_photocard)
            .onPositive(text = "받기", buttonBackgroundColor = R.drawable.btn_round_10_green) {
                LoadingFragmentDialog.loadingMintingDialog.show(
                    childFragmentManager,
                    "loadingMintingDialog"
                )
                nftViewModel.getPhotoCardUrl()
            }
            .onNegative(text = "취소", buttonBackgroundColor = R.drawable.btn_round_main_color) {

            }
    }

    // 민팅 불가 다이얼로그
    private fun noMintingDialog() {
        AwesomeDialog.build(requireActivity())
            .title("조건이 충족되지 않았습니다")
            .body("전월 기부액이 100,000ELN 이상이면\nNFT를 받을 수 있습니다")
            .icon(R.drawable.ic_photocard)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {

            }
    }

    // 충전 확인 다이얼로그(아동) (충전하기 직전)
    private fun confirmFillUpDialog() {
        AwesomeDialog.build(requireActivity())
            .title("충전 하시겠습니까?")
            .body("보유 토큰을 10만 ELN로 충전합니다\n추가 충전은 다음주에 가능합니다")
            .icon(R.drawable.ic_meat)
            .onPositive(text = "충전", buttonBackgroundColor = R.drawable.btn_round_10_green) {
                userViewModel.fillUpTokenChild()
            }
            .onNegative(text = "취소", buttonBackgroundColor = R.drawable.btn_round_main_color) {

            }
    }

    // 충전 불가 다이얼로그(아동) (이미 충전한 경우)
    private fun alreadyFillUpDialog() {
        AwesomeDialog.build(requireActivity())
            .title("충전 불가")
            .body("이번 주에는 이미 충전을 하였습니다\n다음 주에 충전을 해주세요")
            .icon(R.drawable.ic_duck)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {

            }
    }

    // 충전 불가 다이얼로그(아동) (보유 토큰 10만 이상인 경우)
    private fun overFillUpDialog() {
        AwesomeDialog.build(requireActivity())
            .title("충전 불가")
            .body("보유 가능한 토큰은 10만 ELN이 최대입니다\n토큰을 소진하시고 충전해주세요")
            .icon(R.drawable.ic_duck)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {

            }
    }

    // 충전 완료 다이얼로그(아동)
    private fun completedFillUpDialog() {
        AwesomeDialog.build(requireActivity())
            .title("충전 완료")
            .body("충전이 완료되었습니다")
            .icon(R.drawable.ic_duck)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {

            }
    }
}