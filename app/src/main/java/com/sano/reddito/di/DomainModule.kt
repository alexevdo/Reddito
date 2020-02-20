package com.sano.reddito.di

import com.sano.reddito.domain.usecase.LoginUseCase
import com.sano.reddito.domain.usecase.MainUseCase
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val domainModule: Module = module {
    single { LoginUseCase(get()) }
    single { MainUseCase(get(), get()) }
}
