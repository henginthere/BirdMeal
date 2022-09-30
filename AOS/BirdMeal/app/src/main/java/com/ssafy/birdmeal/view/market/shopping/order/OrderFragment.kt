package com.ssafy.birdmeal.view.market.shopping.order

import android.os.Build
import android.text.InputFilter
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentOrderBinding
import com.ssafy.birdmeal.di.ApplicationClass
import com.ssafy.birdmeal.view.home.UserViewModel
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
        }
        initClickListener()

        initViewModelCallBack()

        initNickNameRule()
    }

    private fun initViewModelCallBack(){
        shoppingViewModel.apply {
            // 주문 완료된 경우 주문완료 페이지로 이동
            orderSuccessMsgEvent.observe(viewLifecycleOwner){
                binding.btnBuy.isEnabled = true // 버튼 재활성화
                showToast(it)
                findNavController().navigate(R.id.action_orderFragment_to_orderCompletedFragment)
            }
            donateSuccessMsgEvent.observe(viewLifecycleOwner){
                showToast(it)
            }
            errMsgEvent.observe(viewLifecycleOwner){
                showToast(it)
            }
        }

        userViewModel.apply {
            // 회원정보 수정이 완료된 경우
            userUpdateMsgEvent.observe(viewLifecycleOwner) {
                binding.btnSaveInfo.isEnabled = true // 버튼 재활성화
                showToast(it)
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
            it.isEnabled = false // 버튼 비활성화
            // 상품 컨트랙트 목록 불러오기
            val contractList: MutableList<Trade> =
                (requireActivity().application as ApplicationClass)
                    .getTradeContract(shoppingViewModel.productList.value)

            // 상품 컨트랙트 주문 넣기
            shoppingViewModel.buyingList(contractList, userViewModel.user.value!!.userSeq)
        }

        // 주문자 정보 저장
        btnSaveInfo.setOnClickListener {
            it.isEnabled = false // 버튼 비활성화

            userViewModel.user.value!!.apply { // 정보 업데이트
                userNickname = etName.text.toString()
                userTel = etTelNumber.text.toString()
                userAdd = etAddress.text.toString()
            }
            userViewModel.updateUserProfile()
        }
        // 주소 검색
        btnSearchAddress.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_searchAddressFragment)
        }
    }

    // 닉네임 설정 제한
    private fun initNickNameRule(){
        binding.etName.filters = arrayOf(
            InputFilter { src, _, _, _, _, _ ->
                // val ps = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-흐]+$") // 영문 숫자 한글
                // 영문 숫자 한글 천지인 middle dot[ᆞ]
                val ps = Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")
                if (src.equals("") || ps.matcher(src).matches()) {
                    return@InputFilter src;
                }
                showToast("닉네임은 한글, 영문, 숫자로만 2자 ~ 8자까지 입력 가능합니다.")
                return@InputFilter "";
            },
            InputFilter.LengthFilter(8)
        )
    }

}