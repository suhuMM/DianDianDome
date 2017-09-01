package annotation.com.suhu.measureview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by suhu on 2017/8/23.
 */

public class TestView extends ViewGroup{
    private static final int STANDARD = 100;



    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        init();
    }


    private void init(){
        int a = getWidth();
        int b = getHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec),measureSize(heightMeasureSpec));
    }


    private int measureSize(int measureSpec){
        int result = STANDARD;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }
        if (specMode == MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
        }
        return result;
    }

}
