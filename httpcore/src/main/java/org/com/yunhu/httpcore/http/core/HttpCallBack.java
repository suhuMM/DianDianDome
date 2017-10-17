package org.com.yunhu.httpcore.http.core;

import android.content.Context;
import android.content.DialogInterface;

import org.com.yunhu.httpcore.http.dialog.RProgressDialog;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author suhu
 * @data 2017/10/17.
 * @description
 */

public abstract class HttpCallBack<T> implements Callback<T> ,DialogInterface.OnDismissListener{
    public WeakReference<Context> context;
    private RProgressDialog loadingDialog;

    public HttpCallBack(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        hintDialog();
        if (context == null||context.get()==null){
            return;
        }
        if (response.isSuccessful()){
            onSuccess(response.body());
        }else {
            onFailed((T)response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        hintDialog();
        if (context == null||context.get()==null){
            return;
        }
        onConnectionFailed();
    }

    public void onStart(boolean showProgress){
        if (showProgress) {
            if (loadingDialog == null) {
                loadingDialog = RProgressDialog.getInstance(context.get(), "",
                        false, null);
                loadingDialog.setCanceledOnTouchOutside(false);

            }
            loadingDialog.setCancelable(false);
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    public void hintDialog(){
        if (loadingDialog == null)
            return;
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }




    public abstract void onSuccess(T result);
    public abstract void onFailed(T result);
    public abstract  void onConnectionFailed();
}
