package com.sano.reditto.presentation.main.view

import com.sano.reditto.presentation.base.BaseView
import com.sano.reditto.presentation.model.LinkModel

interface MainView : BaseView {
    fun addLinks(models: List<LinkModel>)
    fun setLinks(models: List<LinkModel>)
    fun setRefreshing(isRefreshing: Boolean)
    fun logout()
    fun openTab(url: String)
}