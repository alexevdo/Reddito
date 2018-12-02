package com.sano.reditto.presentation.main

import com.sano.reditto.di.manager.SessionManager
import com.sano.reditto.domain.usecase.MainUseCase
import com.sano.reditto.presentation.base.BasePresenter
import com.sano.reditto.presentation.main.view.MainView
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.util.handleProgress
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class MainPresenter(
    private val sessionManager: SessionManager,
    private val mainUseCase: MainUseCase
) : BasePresenter<MainView>() {

    val pageSize
        get() = mainUseCase.pageSize

    override fun onViewSet() {
        sessionManager
            .subscribeLogout { view.logout() }
            .addTo(compositeDisposable)

        load()
    }

    fun loadMore(itemCount: Int) =
        mainUseCase.getTop(itemCount)
            .handleProgress { view.setRefreshing(it) }
            .subscribeBy(
                onSuccess = { view.addLinks(it) },
                onError = { view.showError(it.message) })
            .addTo(compositeDisposable)

    fun load() =
        mainUseCase.getTop()
            .handleProgress { view.setRefreshing(it) }
            .subscribeBy(
                onSuccess = { view.setLinks(it) },
                onError = { view.showError(it.message) })
            .addTo(compositeDisposable)

    fun onRevokeAccessToken() =
        mainUseCase.revokeAccessToken()
            .handleProgress { view.setRefreshing(it) }
            .subscribeBy(
                onComplete = { view.notify("Ok") },
                onError = { view.showError(it.message) }
            )
            .addTo(compositeDisposable)

    fun onRevokeRefreshToken() =
        mainUseCase.revokeRefreshToken()
            .handleProgress { view.setRefreshing(it) }
            .subscribeBy(
                onComplete = { view.notify("Ok") },
                onError = { view.showError(it.message) }
            )
            .addTo(compositeDisposable)

    fun logout() = mainUseCase.logout()

    fun onLinkClick(it: LinkModel) = view.openTab(mainUseCase.formatLink(it))
}