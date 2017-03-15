package com.example.okhttp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    private SpotsDialog loadingDialog;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new SpotsDialog(this,"正在加载数据");
        loadingDialog.show();


//        progressDialog = new ProgressDialog(this);
//        progressDialog.show();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i <100 ; i++) {
//                    progressDialog.setProgress(i);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                progressDialog.dismiss();
//            }
//        }).start();
//





    }
}
