package com.sano.reddito.di

import com.sano.reddito.presentation.login.LoginPresenter
import com.sano.reddito.presentation.main.MainPresenter
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val presenterModule: Module = module {
    single { LoginPresenter(get(), get()) }
    single { MainPresenter(get(), get()) }
}
