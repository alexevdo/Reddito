package com.sano.reditto.data.mapper

import com.sano.reditto.data.server.pojo.Link
import com.sano.reditto.data.server.pojo.Listing
import com.sano.reditto.domain.entity.LinkEntity
import com.sano.reditto.domain.entity.PagedLinksEntity

fun Listing<Link>.toPagedLinksEntity() =
    PagedLinksEntity(
        after = after,
        links = children?.map { it.data.toLinkEntity() } ?: emptyList())


fun Link.toLinkEntity() =
    LinkEntity(
        id = id,
        title = title,
        author = author,
        subreddit = subreddit,
        postDate = createdUtc,
        thumbnail = thumbnail,
        score = score,
        numComments = numComments,
        link = permalink
    )
