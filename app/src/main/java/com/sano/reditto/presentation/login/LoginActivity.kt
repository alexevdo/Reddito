package com.sano.reditto.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import com.sano.reditto.R
import com.sano.reditto.presentation.main.MainActivity
import org.koin.android.ext.android.inject
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback

class LoginActivity: AppCompatActivity(), LoginView {

    private val presenter: LoginPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        presenter.setLoginView(this)
    }

    override fun openTab(url: String) =
        CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
            .setShowTitle(true)
            .build()
            .let {
                CustomTabsHelper.addKeepAliveExtra(this@LoginActivity, it.intent)
                CustomTabsHelper
                    .openCustomTab(this@LoginActivity, it, Uri.parse(url), WebViewFallback())
            }


    override fun navigateToMain() =
        startActivity(Intent(this, MainActivity::class.java))

}