package com.sano.reditto.di.manager

import android.content.SharedPreferences

class AuthManager(private val sharedPrefs: SharedPreferences) {

    val API_OAUTH_CLIENTID = "feVnkdeE6BM6tQ"
    val API_OAUTH_REDIRECT = "http://oauth-reditto"
    val RESPONSE_TYPE = "code"
    val DURATION = "permanent"
    val SCOPE = "read"

    val GRANT_TYPE = "authorization_code"

    val API_LOGIN_URL = "https://www.reddit.com/api/v1/authorize.compact?" +
            "client_id=" + API_OAUTH_CLIENTID +
            "&response_type=" + RESPONSE_TYPE +
            "&state=RANDOM_STRING" +
            "&redirect_uri=" + API_OAUTH_REDIRECT +
            "&duration=" + DURATION +
            "&scope=" + SCOPE


    fun isLoggedIn(): Boolean {
        return false
    }

    val apiLoginUrl: String
        get() = API_LOGIN_URL

}