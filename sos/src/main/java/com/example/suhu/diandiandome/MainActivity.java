package com.example.suhu.diandiandome;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

    private Camera.Parameters parameters;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    protected void onClick(View view){
        flashopen();
       start();
    }
    protected void close(View view){
        close();
        flashclose();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        // TODO Auto-generated method stub
            super.handleMessage(msg);
        }

    };

    // 主开启
    public void start() {
        handler.post(startThread);
        handler.post(closeThread);
    }

    // 关闭
    public void close() {
        handler.removeCallbacks(startThread);
        handler.removeCallbacks(closeThread);
        flashclose();
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private void flashopen() {//开启灯
        if (camera == null) {
            camera = Camera.open();
        }
        parameters = camera.getParameters();

        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

        camera.setParameters(parameters);
        camera.startPreview();
    }

    private void flashclose() {//关闭灯
        if (camera == null) {
            camera = Camera.open();
        }
        parameters = camera.getParameters();

        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        camera.setParameters(parameters);
    }

    Runnable startThread = new Runnable() {
        // 将要执行的操作写在线程对象的run方法当中
        public void run() {
            System.out.println("updateThread");
            flashopen();
            try {
                Thread.sleep(100);
                flashclose();
            } catch (InterruptedException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            handler.post(startThread);
        }

    };

    Runnable closeThread = new Runnable() {
        // 将要执行的操作写在线程对象的run方法当中
        public void run() {
            flashclose();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            handler.post(closeThread);
        }

    };



}
