package com.sano.reditto.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sano.reditto.BuildConfig
import com.sano.reditto.data.Repository
import com.sano.reditto.data.server.APIClient
import com.sano.reditto.data.server.AuthAPIClient
import com.sano.reditto.di.manager.AuthManager
import com.sano.reditto.di.manager.REFRESH_GRANT_TYPE
import com.sano.reditto.di.manager.SessionManager
import com.sano.reditto.domain.IRepository
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val AUTH_API_URL = "https://www.reddit.com/"
private const val API_URL = "https://oauth.reddit.com/"

val appModule: Module = module {
    single { provideSharedPrefs(androidApplication().applicationContext) }
    single { AuthManager(get()) }
    single { SessionManager() }
    single { provideGson() }
    single { provideAuthAPIClient(get()) }
    single { provideAPIClient(get(), get(), get(), get()) }
    single { Repository(get(), get(), get(), get()) as IRepository}
}

private fun provideSharedPrefs(context: Context) = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

private fun provideAuthAPIClient(gson: Gson): AuthAPIClient {
    val okHttpClientBuilder = initOkHttpBuilder()

    val retrofitBuilder = Retrofit.Builder()
        .client(okHttpClientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    val retrofit = retrofitBuilder.baseUrl(AUTH_API_URL).build()

    return retrofit.create<AuthAPIClient>(AuthAPIClient::class.java)
}

private fun provideAPIClient(authAPIClient: AuthAPIClient, gson: Gson, authManager: AuthManager,
                             sessionManager: SessionManager): APIClient {

    val okHttpClientBuilder = initOkHttpBuilder()

    okHttpClientBuilder.addAuthInterceptors(authAPIClient, authManager, sessionManager)

    val retrofitBuilder = Retrofit.Builder()
        .client(okHttpClientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    val retrofit = retrofitBuilder.baseUrl(API_URL).build()

    return retrofit.create<APIClient>(APIClient::class.java)
}

private fun OkHttpClient.Builder.addAuthInterceptors(authAPIClient: AuthAPIClient, authManager: AuthManager,
    sessionManager: SessionManager): OkHttpClient.Builder {

    addInterceptor { chain ->
        val original = chain.request()

        val request = original
            .newBuilder()
            .headers(authManager.tokenHeaders)
            .method(original.method(), original.body())
            .build()

        chain.proceed(request)
    }

    authenticator { _, response ->
        if (responseCount(response) >= 2) {
            sessionManager.onLoggedOut()
            return@authenticator null
        }

        val call = authAPIClient.getRefreshAccessToken(
            authManager.clientBasicAuth,
            authManager.refreshToken,
            REFRESH_GRANT_TYPE)

        try {
            val tokenResponse = call.execute()
            return@authenticator if (tokenResponse.code() == 200 && tokenResponse.body() != null) {
                authManager.updateAuthToken(tokenResponse.body()!!)

                response.request().newBuilder()
                    .headers(authManager.tokenHeaders)
                    .build()
            } else {
                authError(authManager, sessionManager)
                return@authenticator null
            }
        } catch (e: IOException) {
            authError(authManager, sessionManager)
            return@authenticator null
        }
    }

    return this
}

private fun authError(authManager: AuthManager, sessionManager: SessionManager) {
    authManager.clearAuth()
    sessionManager.onLoggedOut()
}

private fun Request.Builder.headers(accessHeaders: Map<String, String>): Request.Builder {
    accessHeaders.forEach { header(it.key, it.value) }
    return this
}

private fun responseCount(response: Response): Int {
    var priorResponse = response.priorResponse()
    var result = 1
    while (priorResponse != null) {
        result++
        priorResponse = priorResponse.priorResponse()
    }
    return result
}

private fun initOkHttpBuilder(): OkHttpClient.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClientBuilder
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
    }
    return okHttpClientBuilder
}

private fun provideGson() = GsonBuilder().create()