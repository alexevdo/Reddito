package com.sano.reditto.util

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import com.sano.reditto.R
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context)
        .inflate(layoutId, this, attachToRoot)

fun View.gone() { visibility = View.GONE }
fun View.visible() { visibility = View.VISIBLE}
fun View.visibleOrGone(isVisible: Boolean) { if(isVisible) visible() else gone() }
fun View.isVisible() = visibility == View.VISIBLE

fun View.onClick(listener: (View) -> Unit) = setOnClickListener(listener)

fun Activity.openCustomTab(url: String) =
    CustomTabsIntent.Builder()
        .addDefaultShareMenuItem()
        .setToolbarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        .setShowTitle(true)
        .build()
        .let {
            CustomTabsHelper.addKeepAliveExtra(this@openCustomTab, it.intent)
            CustomTabsHelper
                .openCustomTab(this@openCustomTab, it, Uri.parse(url), WebViewFallback())
        }