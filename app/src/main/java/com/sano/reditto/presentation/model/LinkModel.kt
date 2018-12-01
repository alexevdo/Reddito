package com.sano.reditto.presentation.model

private const val DEFAULT_THUMBNAIL = "default"

class LinkModel (
    val title: String,
    val author: String,
    val subreddit: String,
    val postDate: Long,
    val thumbnail: String?,
    val score: Int,
    val numComments: Int,
    val link: String
) {
    fun hasThumbnail() = thumbnail != null && thumbnail != DEFAULT_THUMBNAIL
}