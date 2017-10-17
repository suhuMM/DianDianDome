package org.com.yunhu.httpcore.http;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author suhu
 * @data 2017/10/17.
 * @description
 */

public interface ApiService {
    /**
     * 定位信息查询
     * @param page
     * @return
     */

    @FormUrlEncoded
    @POST(URL.GPS_LIST)
    Observable<String> getLocation(@Field("page") int page );

    @FormUrlEncoded
    @POST(URL.GPS_LIST)
    Call<String> getLocationCall(@Field("page") int page );
}
