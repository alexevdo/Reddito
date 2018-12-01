package com.sano.reditto.di.manager

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class SessionManager {

    private val logoutSubject = PublishSubject.create<Unit>()

    fun authError() {
        logoutSubject.onNext(Unit)
    }

    fun subscribeLogout(onNext: () -> Unit) =
        logoutSubject.subscribeBy { onNext.invoke() }
}