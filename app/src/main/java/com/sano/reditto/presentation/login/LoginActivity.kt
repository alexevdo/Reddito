package com.sano.reditto.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sano.reditto.R
import com.sano.reditto.presentation.main.MainActivity
import org.koin.android.ext.android.inject

const val CODE_QUERY_PARAMETER = "code"

class LoginActivity : AppCompatActivity(), LoginView {
    private val presenter: LoginPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        presenter.setLoginView(this)
    }

    override fun openTab(url: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .let {
                it.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(it)
            }

    override fun navigateToMain() =
        startActivity(Intent(this, MainActivity::class.java))
            .also { finish() }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.handleUri(intent?.data)
    }

    override fun showError(message: String?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .create()
            .show()
    }
}