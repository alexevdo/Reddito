package com.sano.reditto.data.server.pojo

import com.google.gson.annotations.SerializedName

class Link(
    @SerializedName("subreddit")
    val subreddit: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("author_fullname")
    val authorFullname: String? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("subreddit_name_prefixed")
    val subredditNamePrefixed: String? = null,

    @SerializedName("thumbnail_height")
    val thumbnailHeight: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("thumbnail_width")
    val thumbnailWidth: Int? = null,

    @SerializedName("thumbnail")
    val thumbnail: String? = null,

//    @SerializedName("preview")
//    val preview: Preview? = null,

    @SerializedName("subreddit_id")
    val subredditId: String? = null,

    @SerializedName("id")
    val id: String,

    @SerializedName("permalink")
    val permalink: String,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("created_utc")
    val createdUtc: Long,

    @SerializedName("score")
    val score: Int,

    @SerializedName("num_comments")
    val numComments: Int
)