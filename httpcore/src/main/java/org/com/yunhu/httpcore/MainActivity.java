package org.com.yunhu.httpcore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.com.yunhu.httpcore.http.core.HttpCallBack;
import org.com.yunhu.httpcore.http.HttpRequestMethod;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fun1();
      //  fun2();

    }

    private void fun2() {
                HttpRequestMethod.getLocationCll(1, new HttpCallBack<String>(this) {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG",result);
            }

            @Override
            public void onFailed(String result) {
                Log.e("TAG",result);
            }

            @Override
            public void onConnectionFailed() {
                Log.e("TAG","onConnectionFailed");
            }

            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("TAG","onConnectionFailed");
            }
        });
    }

    private void fun1() {
        HttpRequestMethod.getLocation(1, new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("TAGfun1","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAGfun1","Throwable");
            }

            @Override
            public void onNext(String o) {
                Log.e("TAGfun1",o);
            }
        });
    }
}
