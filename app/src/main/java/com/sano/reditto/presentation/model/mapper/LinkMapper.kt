package com.sano.reditto.presentation.model.mapper

import com.sano.reditto.domain.entity.LinkEntity
import com.sano.reditto.presentation.model.LinkModel

fun LinkEntity.toLinkModel() =
    LinkModel(
        id = id,
        title = title,
        author = author,
        subreddit = subreddit,
        postDate = postDate,
        thumbnail = thumbnail,
        score = score,
        numComments = numComments,
        link = link
    )