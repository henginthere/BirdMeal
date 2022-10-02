package com.ssafy.birdmeal.view.donation

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.*
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentDonateBinding
import com.ssafy.birdmeal.utils.*
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingDonationDialog

class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val donationViewModel by activityViewModels<DonationViewModel>()

    override fun init() {
        binding.apply {
            userVM = userViewModel
            donationVM = donationViewModel
            etAmount.apply {
                addTextChangedListener(CustomTextWatcher(this))
            }
        }

        initViewModelCallBack()
        userViewModel.getUserTokenValue()
        Log.d(TAG, "init: ${userViewModel.userBalance.value}")
        initClickListener()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            userBalance.observe(viewLifecycleOwner) {
                binding.tvBeforeDonate.text = getDecimalFormat(it) + "  ELN"
            }

            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }

        donationViewModel.apply {

            loadingMsgEvent.observe(viewLifecycleOwner) {
                when (it) {

                    // 기부 금액을 입력하지 않은 경우
                    DONATE_EMPTY -> {
                        emptyBalanceDialog()
                    }
                    // 잔액이 부족한 경우
                    DONATE_BALANCE -> {
                        shortageBalanceDialog()
                    }
                    // 기부 준비 완료
                    DONATE_POSSIBLE -> {
                        loadingDonationDialog.show(childFragmentManager, "loadingDialog")
                    }
                    // 기부가 완료 됨
                    DONATE_COMPLETED -> {
                        loadingDonationDialog.dismiss()

                        // 나의 잔액 다시 불러옴
                        userViewModel.getUserTokenValue()

                        // EditText 초기화
                        binding.etAmount.text = null

                        findNavController().popBackStack()

                        setDonateCompleted()
                    }
                }
            }

            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 기부하기
        btnDonte.setOnClickListener {
            val userBalance = userViewModel.userBalance.value ?: 0
            donationViewModel.checkDonate(userBalance, true)

            etAmount.clearFocus()
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    // 기부금액이 입력되지 않음
    private fun emptyBalanceDialog() {
        AwesomeDialog.build(requireActivity())
            .title("기부 알림")
            .body("기부할 금액을 입력해주세요")
            .icon(R.drawable.ic_chicken)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {

            }

    }

    // 기부금액이 부족함
    private fun shortageBalanceDialog() {
        AwesomeDialog.build(requireActivity())
            .title("기부 알림")
            .body("기부 금액이 보유한 토큰보다 많습니다")
            .icon(R.drawable.ic_chicken)
            .onNegative(text = "확인", buttonBackgroundColor = R.drawable.btn_round_10_green) {

            }

    }

//
//    private fun getDecimalFormat(number: Long): String {
//        val decimalFormat = DecimalFormat("#,###")
//        return decimalFormat.format(number)
//    }

}