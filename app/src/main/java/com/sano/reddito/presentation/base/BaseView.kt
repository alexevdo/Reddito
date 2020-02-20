package com.sano.reddito.presentation.base

interface BaseView {
    fun showError(message: String? = null)
    fun notify(message: String)
}