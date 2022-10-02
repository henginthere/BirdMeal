package com.ssafy.birdmeal.view.login.join.card

import android.Manifest
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCardBinding
import com.ssafy.birdmeal.view.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

private val PERMISSIONS_REQUIRED = Manifest.permission.CAMERA

@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding>(R.layout.fragment_card) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun init() {
        binding.loginVM = loginViewModel

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() = with(loginViewModel) {
        errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        // 카드 인식 성공
        childSuccessMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            // 회원 가입 로직 호출
            loginViewModel.join(true)
        }

        // 카드 인식 실패
        childFailMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }

        // 회원가입 성공하면 홈화면으로
        joinSuccessMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            Intent(requireContext(), MainActivity::class.java).apply {
                startActivity(this)
                requireActivity().finish()
            }
        }

        // OCR 인식 성공
        ocrMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            binding.apply {
                containerPreview.visibility = View.GONE
                headerScan.visibility = View.VISIBLE
                btnScan.visibility = View.VISIBLE
            }
        }
    }

    private fun initClickListener() = with(binding) {
        btnScan.setOnClickListener {
            requestPermissionLauncher.launch(PERMISSIONS_REQUIRED)
        }

        // 스캔창 닫기
        btnOcrClose.setOnClickListener {
            containerPreview.visibility = View.GONE
            btnOcrClose.visibility = View.GONE
            headerScan.visibility = View.VISIBLE
            btnScan.visibility = View.VISIBLE
        }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            childFragmentManager.beginTransaction().replace(R.id.container_preview, OcrFragment())
                .commit()
            binding.apply {
                containerPreview.visibility = View.VISIBLE
                btnOcrClose.visibility = View.VISIBLE
                headerScan.visibility = View.GONE
                btnScan.visibility = View.GONE
            }
        } else {
            // PERMISSION NOT GRANTED
            showToast("권한이 거부됨")
        }
    }
}