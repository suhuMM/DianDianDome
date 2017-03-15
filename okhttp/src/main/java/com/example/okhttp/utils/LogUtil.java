package com.example.okhttp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *@author suhu
 *@time 2017/3/14 15:22
 *对Log的简单封装
*/

public class LogUtil {

    private static final int JSON_INDENT = 2;

    private LogUtil() {

    }

    public static void i(String message) {
        i(MyConfig.TAG, message);
    }

    public static void e(String message) {
        e(MyConfig.TAG, message);
    }

    public static void e(String message, Exception e) {
        e(MyConfig.TAG, message, e);
    }

    public static void json(String message) {
        json(MyConfig.TAG, message);
    }

    public static void i(String tag, String message) {
        if (BuildConfig.IS_LOG) {
            Log.i(tag, getInfo(message));
        }
    }

    public static void e(String tag, String message) {
        e(tag, message, null);
    }

    public static void e(String tag, String message, Exception e) {
        if (BuildConfig.IS_LOG) {
            if (e != null) {
                Log.e(tag, getInfo(message), e);
            } else {
                Log.e(tag, getInfo(message));
            }
        }
    }

    public static void json(String tag, String message) {
        if (BuildConfig.IS_LOG) {
            Log.i(tag, getInfo(getJsonString(message)));
        }
    }

    private static String getJsonString(String jsonStr) {
        try {
            jsonStr = jsonStr.trim();
            if (jsonStr.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.toString(JSON_INDENT);
            }
            if (jsonStr.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                return jsonArray.toString(JSON_INDENT);
            }
        } catch (JSONException e) {
        }
        return "Json字符串格式有误:" + jsonStr;
    }

    private static String getInfo(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(getTargetStackTraceElement().getMethodName() + "()"
                + ",(" + getTargetStackTraceElement().getFileName() + ":"
                + getTargetStackTraceElement().getLineNumber() + ")\n")
                .append(message + "\n");
        return sb.toString();
    }

    private static StackTraceElement getTargetStackTraceElement() {
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(LogUtil.class.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }
}
