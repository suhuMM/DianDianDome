package com.example.okhttp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.blankj.utilcode.utils.AppUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hjm on 2016/9/8.
 * 网络加载至少需要两个方法，一个是POST请求，一个是文件下载(为了更新APK使用)
 * 使用鸿洋大神的封装库需要在Application中初始化
 */
public class HttpUtils {

    private static final String[][] MIME_MapTable = {
            //{后缀名，MIME类型} 
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    private static SpotsDialog loadingDialog;
    private static ProgressDialog progressDialog;

    public interface HttpPostCallBackListener {
        void onSuccess(String response);
    }

    public interface HttpGetCallBackListener {
        void onSuccess(String response);
    }


    public static void post(final Context context, String url, Map<String, String> params, final HttpPostCallBackListener httpPostCallBackListener) {
        post(context, url, params, httpPostCallBackListener, true);
    }


    public static void post(final Context context, String url, Map<String, String> params, final HttpPostCallBackListener httpPostCallBackListener, boolean isDialog) {
        init(context);
        if (isDialog) {
            if (loadingDialog == null) {
                loadingDialog = new SpotsDialog(context,"正在加载数据");
            }
            loadingDialog.dismiss();
            loadingDialog.show();
        }
        OkHttpUtils
                .post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                            loadingDialog = null;
                        }
                        LogUtil.e(e.getMessage());
                        ToastUtils.disPlayShort(context, "网络加载出现异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.json(response);
                        String returnStr = null;
                        try {
                            JSONObject object = new JSONObject(response);
                            returnStr = (String) object.get("RETURN");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (TextUtils.isEmpty(returnStr)){
                            try {
                                JSONObject object = new JSONObject(response);
                                returnStr = (String) object.get("return");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                            loadingDialog = null;
                        }
                        if (httpPostCallBackListener != null) {
                            httpPostCallBackListener.onSuccess(response);
                        }
                    }
                });
    }

    private static void init(Context context) {
        if (!NetWorkUtils.isConnect(context)) {
            ToastUtils.disPlayShort(context, "网络连接已断开，请检查网络连接");
            return;
        }
    }

    public static void download(final Context context, String url, String destFileDir, String destFileName, final boolean isApp) {
        init(context);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setProgress(0);
        progressDialog.show();
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(destFileDir, destFileName) {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        progressDialog.setProgress((int) progress);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        LogUtil.i(e.getMessage());
                        ToastUtils.disPlayShort(context, "网络加载出现异常");
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        progressDialog.dismiss();
                        if (isApp) {
                            AppUtils.installApp(context, file);
                        } else {
                            openFile(context, file);
                        }
                    }
                });
    }

    public static void uploadFile(final Context context, String url, File file, final HttpPostCallBackListener httpUploadCallBackListener) {
        init(context);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setProgress(0);
        progressDialog.show();
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        LogUtil.i(e.getMessage());
                        ToastUtils.disPlayShort(context, "网络加载出现异常");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        progressDialog.setProgress((int) progress);
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        progressDialog.dismiss();
                        if (httpUploadCallBackListener != null) {
                            httpUploadCallBackListener.onSuccess(response.toString());
                        }
                    }
                });
    }

    public static void canncelHttp(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性 
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型 
        String type = getMIMEType(file);
        //设置intent的data和Type属性。 
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转 
        context.startActivity(intent);
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。 
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        } 
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。 
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？ 
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }
}
