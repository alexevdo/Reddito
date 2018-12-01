package com.sano.reditto.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int) =
    LayoutInflater.from(context)
        .inflate(layoutId, this, false)

fun View.gone() { visibility = View.GONE }
fun View.visible() { visibility = View.VISIBLE}
fun View.visibleOrGone(isVisible: Boolean) { if(isVisible) visible() else gone() }