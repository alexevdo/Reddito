package com.sano.reditto.data

import com.sano.reditto.data.server.AuthAPIClient
import com.sano.reditto.di.manager.ACCESS_GRANT_TYPE
import com.sano.reditto.di.manager.API_OAUTH_REDIRECT
import com.sano.reditto.di.manager.AuthManager
import com.sano.reditto.domain.IRepository
import com.sano.reditto.util.RxUtil
import io.reactivex.Completable

class Repository(private val authApiClient: AuthAPIClient,
                 private val apiClient: AuthAPIClient,
                 private val authManager: AuthManager) : IRepository {

    override fun updateAccessToken(code: String): Completable =
        authApiClient.getNewAccessToken(authManager.clientBasicAuth, code, API_OAUTH_REDIRECT, ACCESS_GRANT_TYPE)
            .doOnSuccess { authManager.updateAuthToken(it) }
            .ignoreElement()
            .compose(RxUtil.completableTransformer())

    override fun top() {

    }

}