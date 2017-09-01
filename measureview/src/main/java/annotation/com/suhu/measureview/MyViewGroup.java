package annotation.com.suhu.measureview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by suhu on 2017/8/25.
 */

public class MyViewGroup extends ViewGroup{
    private static final int STANDARD = 1000;

    private int screenHeight =1920;
    private int mStart,mEnd,mLastY;
    private Scroller mScroller;

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int c = getHeight();

        int childCount = getChildCount();
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        int height = 0;

        for (int i4 = 0; i4 < childCount; i4++) {
            View child = getChildAt(i4);
            if (child.getVisibility()!=GONE){
               int a = child.getHeight();
                height = screenHeight+height;
                child.layout(l,i4*screenHeight,r,(i4+1)*screenHeight);
            }
        }
        mlp.height = height;
        setLayoutParams(mlp);


    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {

        setMeasuredDimension(measureSize(parentWidthMeasureSpec),measureSize(parentHeightMeasureSpec));


    }

    private int measureSize(int measureSpec){
        int result = STANDARD;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }
        if (specMode == MeasureSpec.AT_MOST){
            result = Math.max(result,specSize);
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd-mStart;
                if (dScrollY>0){
                    if (dScrollY<screenHeight/3){
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY,1000);
                    }else {
                        mScroller.startScroll(0,getScrollY(),0,screenHeight-dScrollY,1000);
                    }
                }else {
                    if (-dScrollY<screenHeight/3){
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY,1000);
                    }else {
                        mScroller.startScroll(0,getScrollY(),0,-screenHeight-dScrollY,1000);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY -y;
                if (getScrollY()<0){
                    dy = 0;
                }
                if (getScaleY()>getHeight()-screenHeight){
                    dy = 0;
                }
                scrollBy(0,dy);
                mLastY = y;
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }
    }
}
