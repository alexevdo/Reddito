package com.sano.reditto.di

import com.sano.reditto.domain.usecase.LoginUseCase
import com.sano.reditto.presentation.login.LoginPresenter
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val domainModule: Module = module {
    single { LoginUseCase(get()) }
}
