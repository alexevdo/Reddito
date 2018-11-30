package com.sano.reditto.data.server;

import com.sano.reditto.data.server.pojo.AccessToken;
import com.sano.reditto.data.server.pojo.Top;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIClient {
    @GET("top")
    Call<Top> getTop(
            @Query("t") String time,
            @Query("after") String after,
            @Query("before") String before,
            @Query("count") Integer count,
            @Query("include_categories") Boolean category,
            @Query("limit") Integer limit);
}
