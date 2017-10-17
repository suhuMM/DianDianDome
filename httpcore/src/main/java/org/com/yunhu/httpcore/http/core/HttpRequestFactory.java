package org.com.yunhu.httpcore.http.core;

import org.com.yunhu.httpcore.http.ApiService;
import org.com.yunhu.httpcore.http.URL;
import org.com.yunhu.httpcore.http.cookie.CookieManger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author suhu
 * @data 2017/10/17.
 * @description
 */

public class HttpRequestFactory {

    static {
        init();
    }

    private static ApiService service;
    private static void init(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .cookieJar(new CookieManger())
                .addInterceptor(new LoggerInterceptor("TAG"))
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static ApiService getCall(){
        return service;
    }
}
