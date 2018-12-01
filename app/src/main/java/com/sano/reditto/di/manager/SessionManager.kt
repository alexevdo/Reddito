package com.sano.reditto.di.manager

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class SessionManager {

    private val logoutSubject = PublishSubject.create<Unit>()

    fun onLoggedOut() {
        logoutSubject.onNext(Unit)
    }

    fun subscribeLogout(onNext: () -> Unit) =
        logoutSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { onNext.invoke() }
}