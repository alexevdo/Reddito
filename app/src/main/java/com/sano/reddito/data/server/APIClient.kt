package com.sano.reddito.data.server

import com.sano.reddito.data.server.pojo.Link
import com.sano.reddito.data.server.pojo.Listing
import com.sano.reddito.data.server.pojo.Thing
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIClient {
    @GET("top")
    fun getTop(
        @Query("t") time: String,
        @Query("after") after: String?,
        @Query("count") count: Int,
        @Query("include_categories") category: Boolean,
        @Query("limit") limit: Int
    ): Single<Thing<Listing<Link>>>
}
