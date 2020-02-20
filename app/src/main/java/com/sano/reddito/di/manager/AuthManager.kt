package com.sano.reddito.di.manager

import android.content.SharedPreferences
import com.sano.reddito.data.server.pojo.AccessToken
import okhttp3.Credentials

private const val API_OAUTH_CLIENT_ID = "feVnkdeE6BM6tQ"
private const val RESPONSE_TYPE = "code"
private const val DURATION = "permanent"
private const val SCOPE = "read"

const val API_OAUTH_REDIRECT = "http://oauth-reditto"
const val API_LOGIN_URL = "https://www.reddit.com/api/v1/authorize.compact?" +
        "client_id=" + API_OAUTH_CLIENT_ID +
        "&response_type=" + RESPONSE_TYPE +
        "&state=RANDOM_STRING" +
        "&redirect_uri=" + API_OAUTH_REDIRECT +
        "&duration=" + DURATION +
        "&scope=" + SCOPE

const val ACCESS_GRANT_TYPE = "authorization_code"
const val REFRESH_GRANT_TYPE = "refresh_token"

const val REFRESH_TOKEN_TYPE_HINT = "refresh_token"
const val ACCESS_TOKEN_TYPE_HINT = "access_token"

private const val ACCESS_TOKEN_PREFS_KEY = "oauth.accesstoken"
private const val REFRESH_TOKEN_PREFS_KEY = "oauth.refreshtoken"
private const val TOKEN_TYPE_PREFS_KEY = "oauth.tokentype"

class AuthManager(private val sharedPrefs: SharedPreferences) {

    fun isOauthUri(uri: String) = uri.startsWith(API_OAUTH_REDIRECT)

    fun updateAuthToken(accessToken: AccessToken) =
        sharedPrefs.edit()
            .apply {
                putString(ACCESS_TOKEN_PREFS_KEY, accessToken.accessToken)
                putString(TOKEN_TYPE_PREFS_KEY, accessToken.tokenType)

                if (!accessToken.refreshToken.isNullOrEmpty())
                    putString(REFRESH_TOKEN_PREFS_KEY, accessToken.refreshToken)
            }
            .apply()


    fun clearAuth() =
        sharedPrefs.edit()
            .apply {
                remove(ACCESS_TOKEN_PREFS_KEY)
                remove(REFRESH_TOKEN_PREFS_KEY)
                remove(TOKEN_TYPE_PREFS_KEY)
            }
            .apply()

    val accessToken: String?
        get() = sharedPrefs.getString(ACCESS_TOKEN_PREFS_KEY, null)

    private val tokenType: String?
        get() = sharedPrefs.getString(TOKEN_TYPE_PREFS_KEY, null)

    val refreshToken: String?
        get() = sharedPrefs.getString(REFRESH_TOKEN_PREFS_KEY, null)

    val clientBasicAuth: String = Credentials.basic(API_OAUTH_CLIENT_ID, "")

    val tokenHeaders: Map<String, String>
        get() = mapOf(
            "Accept" to "application/json",
            "Content-type" to "application/json",
            "Authorization" to "$tokenType $accessToken"
        )

    val isLoggedIn: Boolean
        get() = !sharedPrefs.getString(ACCESS_TOKEN_PREFS_KEY, null).isNullOrEmpty()

}

class EmptyAccessTokenException : IllegalStateException()

class EmptyRefreshTokenException : IllegalStateException()