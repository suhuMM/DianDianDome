package com.example.mymvp.api;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by suhu on 2017/3/15.
 */

public class ApiManager {
    private static final String TAG = "ApiManager";
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 5;
    private OkHttpClient.Builder builder;
    private RequestData requestData;

    private static ApiManager apiManager;


    private ApiManager() {
        //手动创建一个OkHttpClient并设置超时时间
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, "log: "+message);
            }
        });
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
    }



    //获取单例
    public static synchronized ApiManager getInstance(){
        if (apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }



    /**
     *@method 转换成String
     *@author suhu
     *@time 2017/3/15 16:03
     *@param service
     *@param url
    */
    private <T> T configRetrofit(Class<T> service,String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .build();
        return mRetrofit.create(service);
    }

    /**
     *@method RequestData
     * 获取 requestData 对象
     *
    */
    private synchronized RequestData getRequestData(){
        if (requestData == null){
            requestData = configRetrofit(RequestData.class,ApiUrl.URL);
        }
        return requestData;
    }


    /**
     *@author suhu
     *@time 2017/3/15 15:46
     *@param
    */
    public void getData(Callback<String> callback){
        Call<String> call = getRequestData().getBaidu();
        call.enqueue(callback);
    }


    /**
     * @method tngou
     * @author suhu
     * @time 2017/3/15 16:23
     * @param id
     * @param pager
     * @param rows
     * @param callback
    */
    public void tngou(int id,int pager,int rows ,Callback<String> callback ){
        Call<String> call = getRequestData().tngou(id,pager,rows);
        call.enqueue(callback);
    }




}
