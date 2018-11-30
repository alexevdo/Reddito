package com.sano.reditto.di.manager

import android.content.SharedPreferences
import com.sano.reditto.data.server.pojo.AccessToken
import okhttp3.Credentials

private const val API_OAUTH_CLIENT_ID = "feVnkdeE6BM6tQ"
const val API_OAUTH_REDIRECT = "http://oauth-reditto"
private const val RESPONSE_TYPE = "code"
private const val DURATION = "permanent"
private const val SCOPE = "read"

const val API_LOGIN_URL = "https://www.reddit.com/api/v1/authorize.compact?" +
        "client_id=" + API_OAUTH_CLIENT_ID +
        "&response_type=" + RESPONSE_TYPE +
        "&state=RANDOM_STRING" +
        "&redirect_uri=" + API_OAUTH_REDIRECT +
        "&duration=" + DURATION +
        "&scope=" + SCOPE

const val ACCESS_GRANT_TYPE = "authorization_code"
const val REFRESH_GRANT_TYPE = "refresh_token"

private const val ACCESS_TOKEN_PREFS_KEY = "oauth.accesstoken"
private const val REFRESH_TOKEN_PREFS_KEY = "oauth.refreshtoken"
private const val TOKEN_TYPE_PREFS_KEY = "oauth.tokentype"

class AuthManager(private val sharedPrefs: SharedPreferences) {

    fun isOauthUri(uri: String) = uri.startsWith(API_OAUTH_REDIRECT)

    fun updateAuthToken(accessToken: AccessToken) {
        sharedPrefs.edit().putString(ACCESS_TOKEN_PREFS_KEY, accessToken.accessToken).apply()
        sharedPrefs.edit().putString(REFRESH_TOKEN_PREFS_KEY, accessToken.refreshToken).apply()
        sharedPrefs.edit().putString(TOKEN_TYPE_PREFS_KEY, accessToken.tokenType).apply()
    }

    fun authError() {
        sharedPrefs.edit().remove(ACCESS_TOKEN_PREFS_KEY).apply()
        sharedPrefs.edit().remove(REFRESH_TOKEN_PREFS_KEY).apply()
        sharedPrefs.edit().remove(TOKEN_TYPE_PREFS_KEY).apply()
    }

    val accessToken: String? = sharedPrefs.getString(ACCESS_TOKEN_PREFS_KEY, null)
    val tokenType: String? = sharedPrefs.getString(ACCESS_GRANT_TYPE, null)
    val refreshToken: String? = sharedPrefs.getString(REFRESH_TOKEN_PREFS_KEY, null)
    val clientBasicAuth: String = Credentials.basic(API_OAUTH_CLIENT_ID, "")

    val tokenHeaders: Map<String, String> = mapOf(
        "Accept"        to "application/json",
        "Content-type"  to "application/json",
        "Authorization" to "$tokenType $accessToken")

    val isLoggedIn: Boolean
        get() = !sharedPrefs.getString(ACCESS_TOKEN_PREFS_KEY, null).isNullOrEmpty()

}