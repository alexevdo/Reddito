package com.sano.reditto.presentation.main

import com.sano.reditto.di.manager.SessionManager
import com.sano.reditto.domain.usecase.MainUseCase
import com.sano.reditto.presentation.base.BasePresenter
import io.reactivex.rxkotlin.addTo

class MainPresenter(
    private val sessionManager: SessionManager,
    private val mainUseCase: MainUseCase
) : BasePresenter<MainView>() {

    override fun onViewSet() {
        mainUseCase.getTop(0, 10)
            .subscribe(
                {
                    view.addLinks(it)
                }, {
                    view.showError(it.message)
                })
            .addTo(compositeDisposable)
    }
}