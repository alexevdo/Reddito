package com.sano.reditto.domain.usecase

import com.sano.reditto.domain.IRepository
import io.reactivex.Completable

class LoginUseCase(private val repository: IRepository) {
    fun updateAccessToken(code: String): Completable = repository.updateAccessToken(code)
}