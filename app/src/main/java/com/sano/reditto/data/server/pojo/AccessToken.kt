package com.sano.reditto.data.server.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AccessToken {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("token_type")
    var tokenType: String? = null

    @SerializedName("expires_in")
    var expiresIn: Int? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null

    @SerializedName("scope")
    var scope: String? = null
}

