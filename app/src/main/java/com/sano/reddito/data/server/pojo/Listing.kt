package com.sano.reddito.data.server.pojo

import com.google.gson.annotations.SerializedName

class Listing<T>(
    @SerializedName("children")
    val children: List<Thing<T>>? = null,

    @SerializedName("after")
    val after: String? = null
)