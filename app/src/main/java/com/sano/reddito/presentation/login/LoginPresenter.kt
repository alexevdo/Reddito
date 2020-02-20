package com.sano.reddito.presentation.login

import android.net.Uri
import com.sano.reddito.di.manager.API_LOGIN_URL
import com.sano.reddito.di.manager.AuthManager
import com.sano.reddito.domain.usecase.LoginUseCase
import com.sano.reddito.presentation.base.BasePresenter
import com.sano.reddito.presentation.login.view.LoginView
import io.reactivex.rxkotlin.addTo

const val CODE_QUERY_PARAMETER = "code"
const val ERROR_PARAMETER = "error"

class LoginPresenter(private val authManager: AuthManager, private val useCase: LoginUseCase) :
    BasePresenter<LoginView>() {

    override fun onViewSet() =
        if (authManager.isLoggedIn) view.navigateToMain()
        else view.openTab(API_LOGIN_URL, false)

    fun handleUri(uri: Uri?): Boolean {
        if (uri == null || authManager.isOauthUri(toString())) return false

        val error = uri.getQueryParameter(ERROR_PARAMETER)
        if (!error.isNullOrEmpty()) {
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