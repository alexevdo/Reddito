package com.sano.reditto.presentation.login

import android.net.Uri
import com.sano.reditto.di.manager.API_LOGIN_URL
import com.sano.reditto.di.manager.AuthManager
import com.sano.reditto.domain.usecase.LoginUseCase
import com.sano.reditto.presentation.base.BasePresenter
import com.sano.reditto.presentation.login.view.LoginView
import io.reactivex.rxkotlin.addTo

const val CODE_QUERY_PARAMETER = "code"
const val ERROR_PARAMETER = "error"
const val ACCESS_DENIED = "access_denied"

class LoginPresenter(private val authManager: AuthManager, private val useCase: LoginUseCase) :
    BasePresenter<LoginView>() {

    override fun onViewSet() {
        if (authManager.isLoggedIn) view.navigateToMain()
        else view.openTab(API_LOGIN_URL, false)
    }

    fun handleUri(uri: Uri?): Boolean {
        if (uri == null || authManager.isOauthUri(toString())) return false

        val error = uri.getQueryParameter(ERROR_PARAMETER)
        if(!error.isNullOrEmpty()) {
            view.errorLogin()
            return true
        }

        val code = uri.getQueryParameter(CODE_QUERY_PARAMETER)
        if (code.isNullOrEmpty()) {
            return false
        }

        useCase
            .updateAccessToken(code)
            .subscribe({
                view.navigateToMain()
            }, {
                view.showError(it.message)
            })
            .addTo(compositeDisposable)

        return true
    }

    fun signInClick() = view.openTab(API_LOGIN_URL, true)
}