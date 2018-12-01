package com.sano.reditto.presentation.login.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sano.reditto.R
import com.sano.reditto.presentation.login.LoginPresenter
import com.sano.reditto.presentation.main.view.MainActivity
import com.sano.reditto.util.gone
import com.sano.reditto.util.isVisible
import com.sano.reditto.util.onClick
import com.sano.reditto.util.visible
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity(), LoginView {
    private val presenter: LoginPresenter by inject()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.init(this)

        bSignIn.onClick { presenter.signInClick() }

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object: WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                return presenter.handleUri(request.url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                return presenter.handleUri(Uri.parse(url))
            }
        }
    }

    override fun openTab(url: String, isShowWebView: Boolean) {
        if(isShowWebView) webView.visible()
        webView.loadUrl(url)
    }

    override fun errorLogin() {
        webView.gone()
    }

    override fun navigateToMain() =
        startActivity(Intent(this, MainActivity::class.java))
            .also { finish() }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.handleUri(intent?.data)
    }

    override fun showError(message: String?) =
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .create()
            .show()

    override fun notify(s: String) =
        Toast.makeText(this, "Notify $s", Toast.LENGTH_LONG).show()

    override fun onBackPressed() {
        if(webView.isVisible()) {
            webView.gone()
        } else {
            super.onBackPressed()
        }
    }
}