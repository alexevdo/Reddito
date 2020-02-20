package com.sano.reddito.presentation.login.view

import com.sano.reddito.presentation.base.BaseView

interface LoginView : BaseView {
    fun openTab(url: String, isShowWebView: Boolean)
    fun navigateToMain()
    fun errorLogin()
}