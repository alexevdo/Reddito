package com.sano.reditto.data.server.pojo

import com.google.gson.annotations.SerializedName

class Thing<T>(
    @SerializedName("data")
    val data: T
)