package com.example.zhuanpan;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by suhu on 2017/3/21.
 */

public class ZhuanpanView extends ViewGroup {
    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;

    private int mRadius = 200;
    /**
     * 布局时的开始角度
     */
    private float mStartAngle = 0;
    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;

    private ImageView imageView;
    private AnimatorSet set;


    public ZhuanpanView(Context context) {
        super(context);
        init();
    }

    public ZhuanpanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZhuanpanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        set = new AnimatorSet();
    }


    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 获得开始的角度
                 */
                float start = getAngle(mLastX, mLastY);
                /**
                 * 获得当前的角度
                 */
                float end = getAngle(x, y);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else {  // 二、三象限，色角度值是付值
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                ObjectAnimator anim = ObjectAnimator .ofFloat(imageView, "rotation", mStartAngle,mTmpAngle);
                anim.setDuration(3000);
                set.play(anim);
                set.start();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }
}
