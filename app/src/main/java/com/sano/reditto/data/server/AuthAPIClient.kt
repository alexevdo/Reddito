package com.sano.reditto.data.server

import com.sano.reditto.data.server.pojo.AccessToken
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPIClient {

    @FormUrlEncoded
    @POST("api/v1/access_token")
    fun getNewAccessToken(
        @Header("Authorization") authHeader: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String
    ): Single<AccessToken>

    @FormUrlEncoded
    @POST("api/v1/access_token")
    fun getRefreshAccessToken(
        @Header("Authorization") authHeader: String,
        @Field("refresh_token") refreshToken: String?,
        @Field("grant_type") grantType: String
    ): Call<AccessToken>

    @FormUrlEncoded
    @POST("api/v1/revoke_token")
    fun revokeToken(
        @Header("Authorization") authHeader: String,
        @Field("token") token: String,
        @Field("token_type_hint") tokenTypeHint: String
    ): Completable
}
