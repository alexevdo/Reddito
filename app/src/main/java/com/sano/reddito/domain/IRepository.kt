package com.sano.reddito.domain

import com.sano.reddito.domain.entity.PagedLinksEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IRepository {
    fun updateAccessToken(code: String): Completable

    fun getTop(time: String, after: String?, count: Int, category: Boolean, limit: Int): Single<PagedLinksEntity>

    fun revokeAccessToken(): Completable

    fun revokeRefreshToken(): Completable

    fun logout()
}