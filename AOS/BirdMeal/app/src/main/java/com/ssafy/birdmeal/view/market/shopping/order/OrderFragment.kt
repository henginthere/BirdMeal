package com.ssafy.birdmeal.view.market.shopping.order

import android.os.Build
import android.text.InputFilter
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentOrderBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.loading.LoadingFragmentDialog.Companion.loadingOrderDialog
import com.ssafy.birdmeal.view.market.shopping.ShoppingViewModel
import com.ssafy.birdmeal.wrapper.Trade
import java.util.regex.Pattern

class OrderFragment : BaseFragment<FragmentOrderBinding>(R.layout.fragment_order) {

    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        userViewModel.getUserTokenValue()

        binding.apply {
            shoppingVM = shoppingViewModel
            userVM = userViewModel

            if (shoppingViewModel.userRole.value) { // 아동이면 기부금 텍스트 숨김
                tvDonationAmount.visibility = View.GONE
                tvDonationAmountEln.visibility = View.GONE
            }
        }
        initClickListener()

        initViewModelCallBack()

        initNickNameRule()
    }

    private fun initViewModelCallBack() {
        shoppingViewModel.apply {
            // 주문 완료된 경우 주문완료 페이지로 이동
            orderSuccessMsgEvent.observe(viewLifecycleOwner) {
                loadingOrderDialog.dismiss()

                showToast(it)
                findNavController().navigate(R.id.action_orderFragment_to_orderCompletedFragment)

                binding.btnBuy.isEnabled = true // 버튼 재활성화
            }
            donateSuccessMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
            errMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }

        userViewModel.apply {
            // 회원정보 수정이 완료된 경우
            userUpdateMsgEvent.observe(viewLifecycleOwner) {
                showToast(it)
                binding.btnSaveInfo.isEnabled = true // 버튼 재활성화
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initClickListener() = with(binding) {
        // 뒤로가기 버튼 클릭
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        // 결제하기 버튼 클릭
        btnBuy.setOnClickListener {
            if (checkText() && checkEln()) { // 정보 및 결제 금액 유효성 검사
                loadingOrderDialog.show(childFragmentManager, "loadingOrderDialog")

                it.isEnabled = false // 버튼 비활성화
                // 상품 컨트랙트 목록 불러오기
                val contractList: MutableList<Trade> =
                    (requireActivity().application as ApplicationClass)
                        .getTradeContract(shoppingViewModel.productList.value)

                // 상품 컨트랙트 주문 넣기
                shoppingViewModel.buyingList(contractList, userViewModel.user.value!!.userSeq)
            }
        }

        // 주문자 정보 저장
        btnSaveInfo.setOnClickListener {
            if (checkText()) { // 텍스트 유효성검증
                it.isEnabled = false // 버튼 비활성화

                userViewModel.user.value!!.apply { // 정보 업데이트
                    userNickname = etName.text.toString()
                    userTel = etTelNumber.text.toString()
                    userAdd = etAddress.text.toString()
                    userAddDetail = etAddressDetail.text.toString()
                }
                userViewModel.updateUserProfile()
            }
        }
        // 주소 검색
        btnSearchAddress.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_searchAddressFragment)
        }
    }

    // 결제 금액 검사
    private fun checkEln(): Boolean {
        binding.apply {
            if (userViewModel.userELN.value!! - shoppingViewModel.totalAmount.value < 0) {
                showToast("보유 금액이 부족합니다.")
                return false
            } else {
                return true
            }
        }
    }

    // 유저 정보 유효성 검사
    private fun checkText(): Boolean {
        binding.apply {
            if (etName.text.isNullOrEmpty()) {
                showToast("이름을 입력해주세요.")
                return false
            } else if (etTelNumber.text.isNullOrEmpty() || etTelNumber.text!!.length < 9) {
                showToast("연락처를 입력해주세요(9자리 이상).")
                return false
            } else if (etAddress.text.isNullOrEmpty()) {
                showToast("배송지를 입력해주세요.")
                return false
            }
            else if(etAddressDetail.text.isNullOrEmpty()){
                showToast("상세 주소를 입력해주세요.")
                return false
            }
            // 유효한 경우
            return true
        }
    }

    // 닉네임 설정 제한
    private fun initNickNameRule() {
        binding.etName.filters = arrayOf(
            InputFilter { src, _, _, _, _, _ ->
                // val ps = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-흐]+$") // 영문 숫자 한글
                // 영문 숫자 한글 천지인 middle dot[ᆞ]
                val ps =
                    Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")
                if (src.equals("") || ps.matcher(src).matches()) {
                    return@InputFilter src;
                }
                showToast("닉네임은 한글, 영문, 숫자로만 입력 가능합니다.")
                return@InputFilter "";
            },
            InputFilter.LengthFilter(8)
        )
    }

}