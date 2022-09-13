package com.ssafy.birdmeal.view.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentLoginBinding
import com.ssafy.birdmeal.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val userVM by viewModels<LoginViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun init() {
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
        userVM.errMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
        }
        userVM.loginMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            // 홈 화면 이동
//            startActivity(Intent(requireContext(), MainActivity::class.java))
//            requireActivity().finish()
        }
        userVM.joinMsgEvent.observe(viewLifecycleOwner) {
            showToast(it)
            // 회원가입 화면 이동
//            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
    }

    private fun googleSignIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        googleSignInResult.launch(signInIntent)
    }

    private val googleSignInResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        if (it.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val accessToken = account.idToken!!
                val email = account.email!!
                Log.d(TAG, "accessToken: $accessToken")
                Log.d(TAG, "email: $email")
                userVM.googleLogin(accessToken, email)
            } catch (e: ApiException) {
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            }
        } else {
            Log.d(TAG, "${it.resultCode}: ")
            Log.d(TAG, "${it.data}: ")
            Log.d(TAG, "${it.data?.data}: ")
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onDestroyView: logout")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        val client = GoogleSignIn.getClient(requireActivity(), gso)
        client.signOut().addOnCompleteListener {
            Log.d(TAG, "onDestroyView: logout")
        }
    }
}