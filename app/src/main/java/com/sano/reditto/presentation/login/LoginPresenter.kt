package com.sano.reditto.presentation.login

import android.net.Uri
import com.sano.reditto.di.manager.API_LOGIN_URL
import com.sano.reditto.di.manager.AuthManager
import com.sano.reditto.domain.usecase.LoginUseCase
import io.reactivex.disposables.CompositeDisposable

class LoginPresenter(private val authManager: AuthManager, private val useCase: LoginUseCase) {
    private lateinit var loginView: LoginView

    private val compositeDisposable = CompositeDisposable()

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView

        if(authManager.isLoggedIn) {
            loginView.navigateToMain()
        } else {
            loginView.openTab(API_LOGIN_URL)
        }
    }

    fun handleUri(uri: Uri?) {
        if(uri == null || authManager.isOauthUri(toString())) {
            return
        }

        val code = uri.getQueryParameter(CODE_QUERY_PARAMETER)

        if (code != null) {

            useCase
                .updateAccessToken(code)
                .subscribe({
                    loginView.navigateToMain()
                }, {
                    loginView.showError(it.message)
                })
                .let { compositeDisposable.add(it) }

        } else {
            loginView.showError()
        }
    }
}