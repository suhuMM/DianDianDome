package com.example.mymvp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;



/**
 * Created by suhu on 2017/3/15.
 */

public interface RequestData {
    @GET("/")
    Call<String> getBaidu();

    @POST(ApiUrl.COOK)
    Call<String> tngou(@Query("id") int id, @Query("pager") int pager, @Query("rows") int rows);

    @POST(ApiUrl.UPLOAD)
    Call<String> upload(@Query("des") String des );
}
