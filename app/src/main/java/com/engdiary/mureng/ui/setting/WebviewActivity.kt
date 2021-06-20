package com.engdiary.mureng.ui.setting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey.OPEN_SOURCE
import com.engdiary.mureng.constant.IntentKey.PRIVACY_POLICY
import com.engdiary.mureng.constant.URLConstant.INSAT_GRAM_URL
import com.engdiary.mureng.constant.URLConstant.LISENCE_URL
import com.engdiary.mureng.constant.URLConstant.PRIVACY_POLICY_URL
import com.engdiary.mureng.databinding.ActivityPushAlertBinding
import com.engdiary.mureng.databinding.ActivityWebviewBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.push_alert.PushAlertViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_webview.*

@AndroidEntryPoint
class WebviewActivity() : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {
    override val viewModel: WebviewViewModel by viewModels()

    private val mode: String?
        get() = intent.getSerializableExtra("mode") as? String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)

        binding.imgWebviewBack.setOnClickListener { finish() }

        viewModel.setMode(mode!!)

        initWebView(binding.webview)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView) {

        webView.webViewClient = WebViewClient()

        webView.settings.apply {
            javaScriptEnabled = true // 자바 스크립트 허용 여부
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            setSupportZoom(true)
            displayZoomControls = false
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            domStorageEnabled = true
        }

        if (mode == OPEN_SOURCE) {
            webView.loadUrl(LISENCE_URL)
        }

        if (mode == PRIVACY_POLICY) {
            webView.loadUrl(PRIVACY_POLICY_URL)
        }
    }

    override fun onBackPressed() {
        if (binding.webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
