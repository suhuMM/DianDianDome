package org.com.yunhu.httpcore.http;

import org.com.yunhu.httpcore.http.core.HttpCallBack;
import org.com.yunhu.httpcore.http.core.HttpRequestFactory;

import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.com.yunhu.httpcore.http.core.HttpRequestFactory.getCall;

/**
 * @author suhu
 * @data 2017/10/17.
 * @description
 */

public class HttpRequestMethod {


    public static void getLocation(int page , Subscriber subscriber){
        getCall()
                .getLocation(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
             //   .observeOn(AndroidSchedulers.mainThread())
              //  .map(new HttpCallBack<String>())
                .subscribe(subscriber);
    }
    public static void getLocationCll(int page , HttpCallBack callBack ){
        callBack.onStart(true);
        HttpRequestFactory.getCall().getLocationCall(page).enqueue(callBack);

    }

}
