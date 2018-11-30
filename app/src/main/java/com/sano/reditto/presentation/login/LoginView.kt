package com.sano.reditto.presentation.login

interface LoginView {
    fun openTab(url: String)
    fun navigateToMain()
    fun showError(message: String? = null)
}