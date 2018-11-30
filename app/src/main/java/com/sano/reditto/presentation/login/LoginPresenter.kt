package com.sano.reditto.presentation.login

import com.sano.reditto.di.manager.AuthManager

class LoginPresenter(private val authManager: AuthManager) {
    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView

        if(authManager.isLoggedIn()) {
            loginView.navigateToMain()
        } else {
            loginView.openTab(authManager.apiLoginUrl)
        }
    }


}