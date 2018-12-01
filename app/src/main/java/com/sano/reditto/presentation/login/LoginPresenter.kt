package com.sano.reditto.presentation.login

import android.net.Uri
import com.sano.reditto.di.manager.API_LOGIN_URL
import com.sano.reditto.di.manager.AuthManager
import com.sano.reditto.domain.usecase.LoginUseCase
import com.sano.reditto.presentation.base.BasePresenter
import com.sano.reditto.presentation.login.view.CODE_QUERY_PARAMETER
import com.sano.reditto.presentation.login.view.LoginView
import io.reactivex.rxkotlin.addTo

class LoginPresenter(private val authManager: AuthManager, private val useCase: LoginUseCase) :
    BasePresenter<LoginView>() {

    override fun onViewSet() {
        if (authManager.isLoggedIn) view.navigateToMain()
        else view.openTab(API_LOGIN_URL)
    }

    fun handleUri(uri: Uri?) {
        if (uri == null || authManager.isOauthUri(toString())) return

        val code = uri.getQueryParameter(CODE_QUERY_PARAMETER)

        if (code.isNullOrEmpty()) {
            view.showError()
            return
        }

        useCase
            .updateAccessToken(code)
            .subscribe({
                view.navigateToMain()
            }, {
                view.showError(it.message)
            })
            .addTo(compositeDisposable)
    }
}