package com.example.zhuanpan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ZhuanpanView view;
    private ImageView imageView;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (ZhuanpanView) findViewById(R.id.myview);
        imageView = (ImageView) findViewById(R.id.imv);
        tv = (TextView) findViewById(R.id.tv);
        view.setImageView(imageView);


    }



}
