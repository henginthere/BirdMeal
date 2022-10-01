package com.ssafy.birdmeal.view.my_page.edit

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentSearchAddressBinding
import com.ssafy.birdmeal.utils.ADDRESS_API_URL
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.WHITE
import com.ssafy.birdmeal.utils.changeStatusBarColor
import com.ssafy.birdmeal.view.home.UserViewModel

class SearchAddressFragment :
    BaseFragment<FragmentSearchAddressBinding>(R.layout.fragment_search_address) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {
        changeStatusBarColor(requireActivity(), WHITE)

        initWebView()

        initClickListener()
    }

    private fun initWebView() = with(binding) {
        Log.d(TAG, "initWebView: ")
        webView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(BridgeInterface(), "Android")

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.d(TAG, "onPageFinished: ")
                    loadUrl("javascript:sample2_execDaumPostcode();")
                }
            }
            loadUrl(ADDRESS_API_URL)
        }
    }

    inner class BridgeInterface() {
        @JavascriptInterface
        fun processDATA(data: String) {
            userViewModel.setAddress(data)
            findNavController().popBackStack()
        }
    }

    private fun initClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}