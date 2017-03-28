package com.example.surfaceview;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by suhu on 2017/3/22.
 */

public class RotaryTableSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private Context context;
    private float mInitScale = 0.75f;
    private float smallScaleX;
    private float smallScaleY;
    private float mScale = 1;
    private float radius = 0;

    private AnimatorSet set;
    // surfaceView 操作对象
    private SurfaceHolder mSurfaceHolder;
    // 画布
    private Canvas mCanvas;
    // 画笔
    private Paint mPaint;
    private Matrix mMatrixBottom;
    private Matrix mMatrixTop;
    private Matrix mMatrixAdd;
    //图片的 Bitmap 对象
    private Bitmap mBottomBitmap;
    private Bitmap mTopBitmap;
    private Bitmap mAddBitmap;

    // 图片宽高 像素
    private float mBottomX;
    private float mBottomY;
    private float mTopX;
    private float mTopY;
    private float mAddX;
    private float mAddY;
    // 窗口宽高 像素
    private float mWindowX;
    private float mWindowY;
    private float windowhight;
    // 图中心坐标
    private float mPointX;
    private float mPointY;

    // 偏移量
    private float mBottomDeviationX;
    private float mBottomDeviationY;
    private float mTopDeviationX;
    private float mTopDeviationY;
    private float mAddDeviationX;
    private float mAddDeviationY;


    private float mX;
    private float mY;

    private float mDownDegree;
    private float mUpDegree;

    private float mDown = 0f;
    private float mUp;


    private float angle = 15;
    private int numberAngle = 0;


    // 线程池，保证线程以队列方式一个一个执行，不出现内存溢出
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private OnRotatingListener onRotatingListener;

    public interface OnRotatingListener {
        void anglePositionListener(int position);

        void onClickListener();
    }

    public RotaryTableSurfaceView(Context context) {
        super(context);
        this.context = context;
    }

    public RotaryTableSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setOnRotatingListener(OnRotatingListener onRotatingListener) {
        this.onRotatingListener = onRotatingListener;
    }

    @Override
    public void run() {
        if (mBottomBitmap != null) {
            try {
                mCanvas = mSurfaceHolder.lockCanvas();
                mCanvas.drawBitmap(mBottomBitmap, mMatrixBottom, mPaint);
                mCanvas.drawBitmap(mTopBitmap, mMatrixTop, mPaint);
                mCanvas.drawBitmap(mAddBitmap, mMatrixAdd, mPaint);
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            } catch (Exception e) {

            }


        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        resetDraw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                mDownDegree = caculateDegree(mX, mY);
                mDown = caculateDegree(mX, mY);
                float distance = getDistance(mX, mY);
                if (distance < radius) {
                    onRotatingListener.onClickListener();
                }
                break;
            case MotionEvent.ACTION_UP:
                mUp = caculateDegree(event.getX(), event.getY());
                rotary();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //rotary();
                break;
            case MotionEvent.ACTION_MOVE:
                mUpDegree = caculateDegree(event.getX(), event.getY());
                rotating();
                mDownDegree = caculateDegree(event.getX(), event.getY());
                break;
        }
        return true;
    }

    public void setBackgroundBitmap(Bitmap bitmap) {
        init(bitmap);
        resetDraw();
    }

    private void init(Bitmap bitmap) {
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mMatrixBottom = new Matrix();
        mMatrixTop = new Matrix();
        mMatrixAdd = new Matrix();

        mAddBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.add);
        mAddX = mAddBitmap.getWidth();
        mAddY = mAddBitmap.getHeight();


        mBottomBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.backgrund);
        //mBottomBitmap = bitmap;
        mBottomX = mBottomBitmap.getWidth();
        mBottomY = mBottomBitmap.getHeight();


        mTopBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zhuanpan);
        mTopX = mTopBitmap.getWidth();
        mTopY = mTopBitmap.getHeight();
        // 屏幕宽高及中心坐标
        mWindowX = context.getResources().getDisplayMetrics().widthPixels;
        mPointX = mWindowX / 2;

        windowhight = context.getResources().getDisplayMetrics().heightPixels;
        mWindowY = windowhight - 460;
        mPointY = mWindowY / 2;
        if (mWindowX < mBottomX) {
            float tempScale = (mBottomX - mWindowX) / mBottomX;
            if (tempScale > (1 - mInitScale)) {
                mScale = 1 - tempScale;
            }
            mBottomDeviationX = (mWindowX - (mBottomX * mScale)) / 2;
            mBottomDeviationY = (mWindowY - (mBottomY * mScale)) / 2;
            // 缩放倍数
            mMatrixBottom.postScale(mScale, mScale);
            //mMatrixTop.postScale(mScale, mScale);
            mMatrixBottom.postTranslate(mBottomDeviationX, mBottomDeviationY);

        } else {
            smallScaleX = mWindowX / mBottomX;
            smallScaleY = windowhight / mBottomY;
            mMatrixBottom.postScale(smallScaleX, smallScaleY);
        }

        //转盘处理
        if (mWindowX < mTopX) {
            mTopDeviationX = (mWindowX - (mTopX * mScale)) / 2;
            mTopDeviationY = (mWindowY - (mTopY * mScale)) / 2;
        } else {
            mTopDeviationX = (mWindowX - mTopX) / 2;
            mTopDeviationY = (mWindowY - mTopY) / 2;
        }
        mMatrixTop.postTranslate(mTopDeviationX, mTopDeviationY);


        //加入按钮
        mAddDeviationX = (mWindowX - mAddX) / 2;
        mAddDeviationY = (mWindowY - mAddY) / 2;
        mMatrixAdd.postTranslate(mAddDeviationX, mAddDeviationY);
        mMatrixAdd.postScale(mScale, mScale, mPointX, mPointY);
        radius = mAddX * mScale;


    }

    /**
     * 重新画
     */
    private void resetDraw() {
        mExecutorService.execute(this);
    }

    /**
     * 计算角度
     */
    private float caculateDegree(float x, float y) {
        float degree = (float) Math.toDegrees(Math.atan2(y - mPointY, x - mPointX));
        return degree;
    }

    private float getDistance(float x, float y) {
        // 根据圆心坐标计算角度
        float distance = (float) Math
                .sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY)
                        * (y - mPointY)));
        return distance;
    }


    /**
     * @param
     * @method 回转
     * @author suhu
     * @time 2017/3/28 14:27
     */
    private synchronized void rotary() {
        final float devilizationDegree = (mUp - mDown) % 360;


        float remainder = devilizationDegree % 30;
        int number = (int) devilizationDegree / 30;
        if (number > 0) {
            if (remainder <= 15) {
            } else {
                number = number + 1;
            }
        }
        if (number == 0) {
            if (remainder <= 15 && remainder >= -15) {
                number = 0;
            }
            if (remainder > 15) {
                number = 1;
            }
            if (remainder < -15) {
                number = -1;
            }
        }
        float degrees = number * 30 - devilizationDegree;
        mDown = mDownDegree + degrees;

        mMatrixTop.postRotate(degrees, mPointX, mPointY);
        resetDraw();

        numberAngle = numberAngle + number * 30;

        int position = numberAngle % 360 / 30;
        if (position < 0) {
            position = position + 13;
        } else {
            position = position + 1;
        }
        onRotatingListener.anglePositionListener(position);

    }


    /**
     * @param
     * @method 实时转动
     * @author suhu
     * @time 2017/3/28 14:28
     */
    private void rotating() {
        int position;
        float devilizationDegree = mUpDegree - mDownDegree;

        if (mMatrixTop != null) {
            mMatrixTop.postRotate(devilizationDegree, mPointX, mPointY);
            resetDraw();

//            angle = angle + devilizationDegree;
//            float number = angle % 360;
//            if (number >= 0) {
//                position = (int) number / 30 + 1;
//            } else {
//                position = (int) number / 30 - 1;
//            }
//            if (position < 0) {
//                position = position + 13;
//            }
//            onRotatingListener.anglePositionListener(position);
        }


    }
}
