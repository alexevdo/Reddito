package com.sano.reditto.data

import com.sano.reditto.data.server.APIClient
import com.sano.reditto.data.server.AuthAPIClient
import com.sano.reditto.data.server.mapper.toPagedLinksEntity
import com.sano.reditto.di.manager.ACCESS_GRANT_TYPE
import com.sano.reditto.di.manager.API_OAUTH_REDIRECT
import com.sano.reditto.di.manager.AuthManager
import com.sano.reditto.domain.IRepository
import com.sano.reditto.domain.entity.PagedLinksEntity
import com.sano.reditto.util.defaultSchedulers
import io.reactivex.Completable
import io.reactivex.Single

class Repository(
    private val authApiClient: AuthAPIClient,
    private val apiClient: APIClient,
    private val authManager: AuthManager
) : IRepository {

    override fun updateAccessToken(code: String): Completable =
        authApiClient.getNewAccessToken(authManager.clientBasicAuth, code, API_OAUTH_REDIRECT, ACCESS_GRANT_TYPE)
            .doOnSuccess { authManager.updateAuthToken(it) }
            .ignoreElement()
            .defaultSchedulers()

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