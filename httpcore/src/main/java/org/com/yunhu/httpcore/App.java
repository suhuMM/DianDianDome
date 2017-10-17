package org.com.yunhu.httpcore;

import android.app.Application;
import android.content.Context;

/**
 * @author suhu
 * @data 2017/10/17.
 * @description
 */

public class App extends Application{
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
