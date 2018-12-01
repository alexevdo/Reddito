package com.sano.reditto.presentation.main

import com.sano.reditto.presentation.base.BaseView
import com.sano.reditto.presentation.model.LinkModel

interface MainView: BaseView {
    fun addLinks(it: List<LinkModel>)
}