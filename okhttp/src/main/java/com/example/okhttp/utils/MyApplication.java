package com.example.okhttp.utils;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by hjm on 2016/9/4.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化全局Context
        ContextUtil.init(getApplicationContext());
        
        //网络连接配置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }
}
