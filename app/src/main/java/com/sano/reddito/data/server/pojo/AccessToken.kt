package com.sano.reddito.data.server.pojo

import com.google.gson.annotations.SerializedName

class AccessToken(
    @SerializedName("access_token")
    var accessToken: String,

    @SerializedName("token_type")
    var tokenType: String,

    @SerializedName("refresh_token")
    var refreshToken: String?
)

