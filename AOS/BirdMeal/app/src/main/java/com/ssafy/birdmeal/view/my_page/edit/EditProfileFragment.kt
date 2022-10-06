package com.ssafy.birdmeal.view.my_page.edit

import android.content.Intent
import android.content.SharedPreferences
import android.text.InputFilter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.utils.BEIGE
import com.ssafy.birdmeal.utils.USER_SEQ
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<com.ssafy.birdmeal.databinding.FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val userViewModel by activityViewModels<UserViewModel>()

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun init() {
        changeStatusBarColor(requireActivity(), BEIGE)

        binding.userVM = userViewModel

        initViewModelCallBack()

        initClickListener()

        initNickNameRule()
    }

    private fun initViewModelCallBack() = with(userViewModel) {
        userUpdateMsgEvent.observe(viewLifecycleOwner) {
//            showToast(it)
            findNavController().popBackStack()

            binding.btnSave.isEnabled = true // 버튼 재활성화
        }
    }

    private fun initClickListener() = with(binding) {

        // 주소 검색
        btnSearchAddress.setOnClickListener {
            if (etName.text.isNullOrEmpty()) {
                showToast("이름을 입력해주세요.")
            }
            else if (etTelNumber.text.isNullOrEmpty() || etTelNumber.text!!.length < 9) {
                showToast("연락처를 입력해주세요.(9자리 이상)")
            }
            else { // 정보 수정 후 주소 찾기
                userViewModel.user.value!!.apply {
                    userNickname = etName.text.toString()
                    userTel = etTelNumber.text.toString()
                }
                findNavController().navigate(R.id.action_editProfileFragment_to_searchAddressFragment)
            }
        }

        btnSave.setOnClickListener {
            if (checkText()) {
                it.isEnabled = false // 연속 클릭 방지

                userViewModel.user.value!!.apply { // 정보 업데이트
                    userNickname = etName.text.toString()
                    userTel = etTelNumber.text.toString()
                    userAdd = etAddress.text.toString()
                    userAddDetail = etAddressDetail.text.toString()
                }
                userViewModel.updateUserProfile()
            }
        }

        // 지갑 정보 보기
        btnWallet.setOnClickListener {
            WalletInfoDialog().show(childFragmentManager, "WalletInfoDialog")
        }

        // 로그아웃
        btnLogout.setOnClickListener {
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

        // 뒤로가기
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    // 유저 정보 유효성 검사
    private fun checkText(): Boolean {
        binding.apply {
            if (etName.text.isNullOrEmpty()) {
                showToast("이름을 입력해주세요.")
                return false
            } else if (etTelNumber.text.isNullOrEmpty() || etTelNumber.text!!.length < 9) {
                showToast("연락처를 입력해주세요.")
                return false
            } else if (etAddress.text.isNullOrEmpty()) {
                showToast("배송지를 입력해주세요.")
                return false
            } else if (etAddressDetail.text.isNullOrEmpty()) {
                showToast("배송지 상세주소를 입력해주세요.")
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