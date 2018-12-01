package com.sano.reditto.util

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.defaultSchedulers(): Single<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.defaultSchedulers(): Completable =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.handleProgress(progressVisibility: (Boolean) -> Unit): Single<T> =
    this.doOnSubscribe { progressVisibility.invoke(true) }
        .doOnSuccess { progressVisibility.invoke(false) }
        .doOnError { progressVisibility.invoke(false) }

fun Completable.handleProgress(progressVisibility: (Boolean) -> Unit): Completable =
    this.doOnSubscribe { progressVisibility.invoke(true) }
        .doOnComplete { progressVisibility.invoke(false) }
        .doOnError { progressVisibility.invoke(false) }