package com.sano.reditto.presentation.login.view

import com.sano.reditto.presentation.base.BaseView

interface LoginView: BaseView{
    fun openTab(url: String)
    fun navigateToMain()
    fun errorLogin()
}