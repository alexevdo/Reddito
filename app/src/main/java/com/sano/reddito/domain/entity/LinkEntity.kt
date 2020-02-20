package com.sano.reddito.domain.entity

class LinkEntity(
    val id: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val postDate: Long,
    val thumbnail: String?,
    val score: Int,
    val numComments: Int,
    val link: String
)