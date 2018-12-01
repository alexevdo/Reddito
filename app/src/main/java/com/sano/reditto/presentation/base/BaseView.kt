package com.sano.reditto.presentation.base

interface BaseView {
    fun showError(message: String? = null)
    fun notify(s: String)
}