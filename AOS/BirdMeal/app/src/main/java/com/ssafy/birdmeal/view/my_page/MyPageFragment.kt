package com.ssafy.birdmeal.view.my_page

import android.content.Intent
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
import com.ssafy.birdmeal.view.login.LoginActivity
import com.ssafy.birdmeal.view.my_page.history.donation.MyDonationHistoryFragment
import com.ssafy.birdmeal.view.my_page.history.order.MyOrderHistoryFragment

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val nftViewModel by activityViewModels<NFTViewModel>()

    private lateinit var myOrderHistoryFragment: MyOrderHistoryFragment
    lateinit var myDonationHistoryFragment: MyDonationHistoryFragment

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        userViewModel.getUserTokenValue()
        binding.userVM = userViewModel
        userViewModel.getUserInfo()

        initViewModelCallBack()
        initClickListener()
        initFragmentManager()
        initChip()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            successMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
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
                }
            }

            userELN.observe(viewLifecycleOwner) {
                binding.tvEln.text = getDecimalFormat(it) + "  ELN"
            }
        }

        nftViewModel.apply {
            mintingMsgEvent.observe(viewLifecycleOwner) {
                LoadingFragmentDialog.loadingMintingDialog.dismiss()
                userViewModel.getUserInfo()
                CompletedMintingDialog(it).show(childFragmentManager, "CompletedMintingDialog")
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
                val dialog = FillUpMoneyDialog(requireContext(), listener)
                dialog.show(childFragmentManager, "FillUpMoneyDialog")
            }
        }

        // my nft 보기
        btnMyNft.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myNftListFragment)
        }

        // 로그아웃
        toolbar.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build()
            val client = GoogleSignIn.getClient(requireActivity(), gso)
            client.signOut().addOnCompleteListener {
                showToast("로그아웃 완료")
            }
            Intent(requireContext(), LoginActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
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
        override fun onItemClick(requestMoney: Int) {
            userViewModel.fillUpToken(requestMoney)
            loadingFillUpDialog.show(childFragmentManager, "loadingFillUpDialog")
        }
    }

    private fun initFragmentManager() = with(binding) {

        myOrderHistoryFragment = MyOrderHistoryFragment()
        myDonationHistoryFragment = MyDonationHistoryFragment()

        childFragmentManager.beginTransaction().replace(R.id.container_view, myOrderHistoryFragment)
            .commit()
    }

    private fun initChip() = with(binding) {
        chipMenuGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val chip = chipMenuGroup.children.find { (it as Chip).isChecked }
            val tag = (chip as Chip).tag

            when (tag) {
                "1" -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_view, myOrderHistoryFragment)
                        .commit()
                }
                "2" -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_view, myDonationHistoryFragment)
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
                nftViewModel.getPhotoCardUrl()
                LoadingFragmentDialog.loadingMintingDialog.show(
                    childFragmentManager,
                    "loadingMintingDialog"
                )
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