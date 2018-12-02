package com.sano.reditto.data

import com.sano.reditto.data.mapper.toPagedLinksEntity
import com.sano.reditto.data.server.APIClient
import com.sano.reditto.data.server.AuthAPIClient
import com.sano.reditto.di.manager.*
import com.sano.reditto.domain.IRepository
import com.sano.reditto.domain.entity.PagedLinksEntity
import com.sano.reditto.util.defaultSchedulers
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

class Repository(
    private val authApiClient: AuthAPIClient,
    private val apiClient: APIClient,
    private val authManager: AuthManager
) : IRepository {

    override fun updateAccessToken(code: String): Completable =
        authApiClient
            .getNewAccessToken(authManager.clientBasicAuth, code, API_OAUTH_REDIRECT, ACCESS_GRANT_TYPE)
            .doOnSuccess { authManager.updateAuthToken(it) }
            .ignoreElement()
            .defaultSchedulers()

    override fun revokeAccessToken(): Completable {
        val accessToken = authManager.accessToken
            ?: return Completable.error(EmptyAccessTokenException())

        return authApiClient
            .revokeToken(authManager.clientBasicAuth, accessToken, ACCESS_TOKEN_TYPE_HINT)
            .defaultSchedulers()
    }

    override fun revokeRefreshToken(): Completable {
        val refreshToken = authManager.refreshToken
            ?: return Completable.error(EmptyRefreshTokenException())

        return authApiClient
            .revokeToken(authManager.clientBasicAuth, refreshToken, REFRESH_TOKEN_TYPE_HINT)
            .defaultSchedulers()
    }

    override fun logout() {
        authManager.clearAuth()

        revokeAccessToken().subscribeBy(
            onError = { it.printStackTrace() }
        )
        revokeRefreshToken().subscribeBy(
            onError = { it.printStackTrace() }
        )
    }

    override fun getTop(
        time: String,
        after: String?,
        count: Int,
        category: Boolean,
        limit: Int
    ): Single<PagedLinksEntity> =
        apiClient.getTop(time, after, count, category, limit)
            .map { it.data.toPagedLinksEntity() }
            .defaultSchedulers()
}