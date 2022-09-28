package com.ssafy.birdmeal.view.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.ssafy.birdmeal.MainActivity
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentLoginBinding
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel by activityViewModels<LoginViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val loadingDialog by lazy { LoadingDialog("로그인중...") }

    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)

        initGso()

        initClickListener()

        initViewModelCallBack()
    }

    private fun initGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(Scopes.EMAIL))
            .requestServerAuthCode(resources.getString(R.string.google_client_key))
            .requestEmail()
            .requestIdToken(getString(R.string.google_client_key))
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun initClickListener() = with(binding) {
        // 구글 소셜 로그인 버튼 클릭시
        ivGoogle.setOnClickListener {
            googleSignIn()
        }
    }

    private fun initViewModelCallBack() {
        loginViewModel.errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
        loginViewModel.loginMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            // 홈 화면 이동
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        loginViewModel.joinMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            // 회원가입 화면 이동
            loadingDialog.dismiss()
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
    }

    private fun googleSignIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        googleSignInResult.launch(signInIntent)
    }

    private val googleSignInResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        if (it.resultCode == Activity.RESULT_OK) {
            handleSignInResult(task)
        } else {
            showToast("구글 서버에 등록되지 않은 서비스 입니다.")
            Log.d(TAG, "GoogleSignInAccount error: ${it}")
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val accessToken = account.idToken!!
            val email = account.email!!
            Log.d(TAG, "accessToken: $accessToken")
            Log.d(TAG, "email: $email")

            loadingDialog.show(childFragmentManager, "loadingDialog")
            loginViewModel.googleLogin(accessToken, email)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}