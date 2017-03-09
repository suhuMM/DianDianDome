package com.example.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/*方向传感器实现指南针*/

public class MainActivity extends Activity {
    private SensorManager manager;
    private Sensor sensor;
    private SensorListener listener = new SensorListener();
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    public final  class SensorListener implements SensorEventListener {
        private float preDegree = 0.0f;
        @Override
        public void onSensorChanged(SensorEvent event) {
            float degree = event.values[0];  // 参数中的第一个值就是方向传感器传回的度数

            RotateAnimation animation = new RotateAnimation(preDegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(300);
            mImageView.setAnimation(animation);
            preDegree = degree;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != manager){
            manager.unregisterListener(listener);
        }
    }
}