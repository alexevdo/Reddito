package com.sano.reditto.presentation.login

import com.sano.reditto.presentation.base.BaseView

interface LoginView: BaseView{
    fun openTab(url: String)
    fun navigateToMain()
}