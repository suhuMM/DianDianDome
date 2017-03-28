package com.example.surfaceview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private RotaryTableSurfaceView rotaryTableSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotaryTableSurfaceView = (RotaryTableSurfaceView) findViewById(R.id.view);
        textView = (TextView) findViewById(R.id.tv_position);

        Glide.with(this)
               // .load("http://dev-kupao.mobifox.cn////Beauty//sportsdata//starts//2017-03-23//58d3337532bb8.png")
                .load("http://img1.3lian.com/img2008/14/04/0113.jpg")
                .asBitmap()
                .skipMemoryCache(false)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        rotaryTableSurfaceView.setBackgroundBitmap(resource);
                    }
                });
        rotaryTableSurfaceView.setOnRotatingListener(new RotaryTableSurfaceView.OnRotatingListener() {
            @Override
            public void anglePositionListener(int position) {
                textView.setText(position+"");
            }

            @Override
            public void onClickListener() {
                Toast.makeText(MainActivity.this,"加入",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
