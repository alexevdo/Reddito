package com.sano.reditto.util

import java.text.DecimalFormat


private val NUM_SUFFIXES = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')

fun numToK(number: Int): String {
    val value = Math.floor(Math.log10(number.toDouble())).toInt()
    val base = value / 3

    return if (value >= 3 && base < NUM_SUFFIXES.size) {
        "${DecimalFormat("#0.#").format(number / Math.pow(10.0, (base * 3).toDouble()))}${NUM_SUFFIXES[base]}".trim()
    } else {
        "$number"
    }
}
