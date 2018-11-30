package com.sano.reditto.data.server;

import com.sano.reditto.data.server.pojo.AccessToken;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthAPIClient {

    @FormUrlEncoded
    @POST("api/v1/access_token")
    Single<AccessToken> getNewAccessToken(
            @Header("Authorization") String authHeader,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("api/v1/access_token")
    Call<AccessToken> getRefreshAccessToken(
            @Header("Authorization") String authHeader,
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType);
}
