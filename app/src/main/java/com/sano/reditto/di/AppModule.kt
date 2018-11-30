package com.sano.reditto.di

import android.content.Context
import com.sano.reditto.di.manager.AuthManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModule: Module = module {
    single { provideSharedPrefs(androidApplication().applicationContext) }
    single { AuthManager(get()) }
}

private fun provideSharedPrefs(context: Context) = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

