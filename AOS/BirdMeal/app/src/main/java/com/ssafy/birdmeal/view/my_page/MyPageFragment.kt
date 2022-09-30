package com.ssafy.birdmeal.view.my_page

import android.content.Intent
import android.util.Log
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
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.utils.getDecimalFormat
import com.ssafy.birdmeal.view.donation.nft.CompletedMintingDialog
import com.ssafy.birdmeal.view.donation.nft.NFTViewModel
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog
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
        if (userViewModel.user.value?.userChargeState == true) {
            binding.btnFillUpMoney.setImageResource(R.drawable.btn_charged)
        }
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
            userELN.observe(viewLifecycleOwner) {
                binding.tvEln.text = getDecimalFormat(it) + "  ELN"
            }
            user.observe(viewLifecycleOwner) {
                Log.d(TAG, "initViewModelCallBack: userObserve")
                if (it.userChargeState)
                    binding.btnFillUpMoney.setImageResource(R.drawable.btn_charged)
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
            val dialog = FillUpMoneyDialog(requireContext(), listener)
            dialog.show()
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
}