package com.ssafy.birdmeal.view.my_page

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentMyPageBinding
import com.ssafy.birdmeal.utils.getDecimalFormat
import com.ssafy.birdmeal.view.home.UserViewModel
import com.ssafy.birdmeal.view.my_page.history.donation.MyDonationHistoryFragment
import com.ssafy.birdmeal.view.my_page.history.order.MyOrderHistoryFragment

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val userViewModel by activityViewModels<UserViewModel>()
    lateinit var myOrderHistoryFragment: MyOrderHistoryFragment
    lateinit var myDonationHistoryFragment: MyDonationHistoryFragment

    override fun init() {
        userViewModel.getUserTokenValue()
        binding.userVM = userViewModel
        initViewModelCallBack()
        initClickListener()
        initFragmentManager()
        initChip()
    }

    private fun initViewModelCallBack() {
        userViewModel.apply {
            successMsgEvent.observe(viewLifecycleOwner){
                showToast(it)
            }
            userELN.observe(viewLifecycleOwner){
                binding.tvEln.text = getDecimalFormat(it) +"  ELN"
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

//        // my nft 보기
//        btnMyNft.setOnClickListener{
//
//        }

//        // 로그아웃
//        btnLogout.setOnClickListener {
//            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .build()
//            val client = GoogleSignIn.getClient(requireActivity(), gso)
//            client.signOut().addOnCompleteListener {
//                showToast("로그아웃 완료")
//            }
//            Intent(requireContext(), LoginActivity::class.java).apply {
//                startActivity(this)
//            }
//        }
    }

    // 토큰 충전 다이얼로그 리스너
    private val listener = object : FillUpMoneyListener{
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

}