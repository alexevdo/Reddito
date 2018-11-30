package com.sano.reditto.di

import com.sano.reditto.presentation.login.LoginPresenter
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val presenterModule: Module = module {
    single { LoginPresenter(get()) }
}
