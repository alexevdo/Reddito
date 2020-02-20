package com.sano.reddito

import LocaleContextWrapper
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.sano.reddito.di.appModule
import com.sano.reddito.di.domainModule
import com.sano.reddito.di.presenterModule
import org.koin.android.ext.android.startKoin
import saschpe.android.customtabs.CustomTabsActivityLifecycleCallbacks
import java.util.*

@Suppress("unused")
class RedittoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        registerActivityLifecycleCallbacks(CustomTabsActivityLifecycleCallbacks())
        startKoin(this, listOf(appModule, presenterModule, domainModule))
    }

    override fun attachBaseContext(base: Context) = super.attachBaseContext(LocaleContextWrapper.wrap(base, Locale.US))
}