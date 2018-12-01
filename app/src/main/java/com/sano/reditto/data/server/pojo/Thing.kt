package com.sano.reditto.data.server.pojo

import com.google.gson.annotations.SerializedName

class Thing<T>(
    @SerializedName("kind")
    val kind: String,

    @SerializedName("data")
    val data: T
)