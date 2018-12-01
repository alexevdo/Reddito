package com.sano.reditto.presentation.main

import com.sano.reditto.di.manager.SessionManager
import com.sano.reditto.domain.usecase.MainUseCase
import com.sano.reditto.presentation.base.BasePresenter
import com.sano.reditto.presentation.main.view.MainView
import io.reactivex.rxkotlin.addTo

class MainPresenter(
    private val sessionManager: SessionManager,
    private val mainUseCase: MainUseCase
) : BasePresenter<MainView>() {
    val pageSize: Int = 10

    override fun onViewSet() {
        load()
    }

    fun loadMore(itemCount: Int) {
        view.setRefreshing(true)
        mainUseCase.getTop(pageSize, itemCount)
            .subscribe(
                {
                    view.setRefreshing(false)
                    view.addLinks(it)
                }, {
                    view.setRefreshing(false)
                    view.showError(it.message)
                })
            .addTo(compositeDisposable)
    }

    fun load() {
        view.setRefreshing(true)
        mainUseCase.getTop(pageSize)
            .subscribe(
                {
                    view.setRefreshing(false)
                    view.setLinks(it)
                }, {
                    view.setRefreshing(false)
                    view.showError(it.message)
                })
            .addTo(compositeDisposable)
    }
}