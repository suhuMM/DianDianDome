package com.example.zhuanpan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by suhu on 2017/3/20.
 */

public class MyView extends ViewGroup {
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;
    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;
    private int mRadius = 200;

    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;
    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;
    /**
     * 布局时的开始角度
     */
    private float mStartAngle = 0;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    /**
     * /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    private ImageView imageView;

    private GestureDetector gestureDetector;
    private boolean isRotating = true;


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public void setImageView(ImageView v) {
        imageView = v;
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        mStartAngle %= 360;
//        //旋转动画
//        Animation animation = new RotateAnimation(mStartAngle,mTmpAngle);
//        animation.setDuration(500);
//        animation.setRepeatCount(1);
//        animation.setFillAfter(true);
//        this.startAnimation(animation);
//    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ViewHelper.setRotation(imageView, mStartAngle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        // Log.e("TAG", "x = " + x + " , y = " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;

                // 如果当前已经在快速滚动
                if (isFling) {
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }

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

                // Log.e("TAG", "start = " + start + " , end =" + end);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else {  // 二、三象限，色角度值是付值
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                // 重新布局
                requestLayout();

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:

                // 计算，每秒移动的角度
                float anglePerSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);

                // Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
                // mTmpAngle);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
                    // post一个任务，去自动滚动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
                    return true;
                }

                break;
        }
        return super.onTouchEvent(event);


    }

//    public boolean dispatchTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//
//        // Log.e("TAG", "x = " + x + " , y = " + y);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                mLastX = x;
//                mLastY = y;
//                mDownTime = System.currentTimeMillis();
//                mTmpAngle = 0;
//
//                // 如果当前已经在快速滚动
//                if (isFling) {
//                    // 移除快速滚动的回调
//                    removeCallbacks(mFlingRunnable);
//                    isFling = false;
//                    return true;
//                }
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                /**
//                 * 获得开始的角度
//                 */
//                float start = getAngle(mLastX, mLastY);
//                /**
//                 * 获得当前的角度
//                 */
//                float end = getAngle(x, y);
//
//                // Log.e("TAG", "start = " + start + " , end =" + end);
//                // 如果是一、四象限，则直接end-start，角度值都是正值
//                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
//                    mStartAngle += end - start;
//                    mTmpAngle += end - start;
//                } else {  // 二、三象限，色角度值是付值
//                    mStartAngle += start - end;
//                    mTmpAngle += start - end;
//                }
//                // 重新布局
//                requestLayout();
//
//                mLastX = x;
//                mLastY = y;
//
//                break;
//            case MotionEvent.ACTION_UP:
//
//                // 计算，每秒移动的角度
//                float anglePerSecond = mTmpAngle * 1000
//                        / (System.currentTimeMillis() - mDownTime);
//
//                // Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
//                // mTmpAngle);
//
//                // 如果达到该值认为是快速移动
//                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
//                    // post一个任务，去自动滚动
//                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));
//
//                    return true;
//                }
//
//                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
//                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
//                    return true;
//                }
//
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }

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

    /**
     * 自动滚动的任务
     *
     * @author zhy
     */
    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 30);
            // 逐渐减小这个值
            angelPerSecond /= 1.0666F;
            postDelayed(this, 30);
            // 重新布局
            requestLayout();
        }
    }

}