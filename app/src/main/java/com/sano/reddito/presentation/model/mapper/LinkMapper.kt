package com.sano.reddito.presentation.model.mapper

import com.sano.reddito.domain.entity.LinkEntity
import com.sano.reddito.presentation.model.LinkModel

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