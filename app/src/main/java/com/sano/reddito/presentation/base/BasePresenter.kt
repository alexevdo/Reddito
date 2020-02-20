package com.sano.reddito.presentation.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : BaseView> {
    protected lateinit var view: T
    protected val compositeDisposable = CompositeDisposable()

    fun init(view: T) {
        this.view = view
        onViewSet()
    }

    abstract fun onViewSet()

    @CallSuper
    fun clear() {
        compositeDisposable.clear()
    }
}