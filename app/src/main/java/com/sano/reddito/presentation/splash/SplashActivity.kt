package com.sano.reddito.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sano.reddito.di.manager.AuthManager
import com.sano.reddito.presentation.login.view.LoginActivity
import com.sano.reddito.presentation.main.view.MainActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val authManager: AuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(
            if (authManager.isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
        )
        finish()
    }
}