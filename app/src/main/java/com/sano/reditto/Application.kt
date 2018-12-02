package com.sano.reditto

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.sano.reditto.di.appModule
import com.sano.reditto.di.domainModule
import com.sano.reditto.di.presenterModule
import org.koin.android.ext.android.startKoin
import saschpe.android.customtabs.CustomTabsActivityLifecycleCallbacks
import java.util.*

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        registerActivityLifecycleCallbacks(CustomTabsActivityLifecycleCallbacks())
        startKoin(this, listOf(appModule, presenterModule, domainModule))
    }

    override fun attachBaseContext(base: Context) = super.attachBaseContext(LocaleContextWrapper.wrap(base, Locale.US))
}