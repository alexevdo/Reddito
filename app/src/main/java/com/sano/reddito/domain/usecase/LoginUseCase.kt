package com.sano.reddito.domain.usecase

import com.sano.reddito.domain.IRepository
import io.reactivex.Completable

class LoginUseCase(private val repository: IRepository) {
    fun updateAccessToken(code: String): Completable = repository.updateAccessToken(code)
}