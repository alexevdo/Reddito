package com.sano.reditto.domain

import io.reactivex.Completable

interface IRepository {
    fun updateAccessToken(code: String): Completable
    fun top()
}