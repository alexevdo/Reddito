package com.sano.reddito.data.server.pojo

import com.google.gson.annotations.SerializedName

class Link(
    @SerializedName("subreddit")
    val subreddit: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("id")
    val id: String,

    @SerializedName("permalink")
    val permalink: String,

    @SerializedName("created_utc")
    val createdUtc: Long,

    @SerializedName("score")
    val score: Int,

    @SerializedName("num_comments")
    val numComments: Int
)