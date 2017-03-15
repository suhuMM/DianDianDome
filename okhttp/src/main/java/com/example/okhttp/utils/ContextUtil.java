package com.example.okhttp.utils;

import android.content.Context;

/**
 * Created by hjm on 2016/12/19/019.
 */

public class ContextUtil {

    private ContextUtil() {
        
    }

    public static Context context;

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        context = context.getApplicationContext();
    }
}
