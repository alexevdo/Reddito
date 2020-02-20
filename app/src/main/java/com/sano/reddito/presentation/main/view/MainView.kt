package com.sano.reddito.presentation.main.view

import com.sano.reddito.presentation.base.BaseView
import com.sano.reddito.presentation.model.LinkModel

interface MainView : BaseView {
    fun addLinks(models: List<LinkModel>)
    fun setLinks(models: List<LinkModel>)
    fun setRefreshing(isRefreshing: Boolean)
    fun logout()
    fun openTab(url: String)
}