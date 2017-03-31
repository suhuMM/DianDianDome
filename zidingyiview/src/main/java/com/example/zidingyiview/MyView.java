package com.example.zidingyiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by suhu on 2017/3/30.
 */

public class MyView extends View{
    private float radius;
    private int color,textColor;
    private String text;
    private float size;

    private Paint paint;

    private float x,y;

    public MyView(Context context) {
        super(context);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    private void init(Context context ,AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyView);
        radius = array.getFloat(R.styleable.MyView_radius,60f);
        color = array.getColor(R.styleable.MyView_color,getResources().getColor(R.color.radius_color));
        text = array.getString(R.styleable.MyView_text);
        size = array.getFloat(R.styleable.MyView_text_size,20f);
        textColor = array.getColor(R.styleable.MyView_text_color,getResources().getColor(R.color.text_color));
        x = array.getFloat(R.styleable.MyView_left,0);
        y = array.getFloat(R.styleable.MyView_right,0);
        array.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x,y,radius,paint);
        canvas.drawPoint(x,y,paint);

        paint.setTextSize(size);
        paint.setColor(textColor);
        float textSize = paint.measureText(text);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);

        Paint.FontMetrics fm = paint.getFontMetrics();
        float baseline1 = y - (fm.ascent + fm.descent) / 2;
        float start = x-textSize/2;
        canvas.drawText(text,start,baseline1,paint);

        paint.setColor(Color.argb(255,255,0,0));
        canvas.drawLine(0,baseline1,getWidth(),baseline1,paint);

        paint.setTextSize(20);
        float start1 = getWidth()-paint.measureText("baseline")-10;
        canvas.drawText("baseline",start1,baseline1,paint);


        paint.setColor(Color.argb(255,0,255,0));
        canvas.drawLine(0,y-fm.descent,getWidth(),y-fm.descent,paint);
        float start2 = getWidth()-paint.measureText("descent")-10;
        canvas.drawText("descent",start2,y-fm.descent,paint);



        paint.setColor(Color.argb(255,0,0,255));
        canvas.drawLine(0,y-fm.ascent,getWidth(),y-fm.ascent,paint);

        float start3 = getWidth()-paint.measureText("ascent")-10;
        canvas.drawText("ascent",start3,y-fm.ascent,paint);

        int width = getWidth();
        int height = getHeight();
        int interval = 50;

        paint.setColor(Color.rgb(0,0,0));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        int x_number = width/interval;
        for (int i = 1; i < x_number; i++) {
            canvas.drawLine(i*interval,0,i*interval,height,paint);
        }
        int y_number = height/interval;
        for (int i = 1; i <= y_number; i++) {
            canvas.drawLine(0,i*interval,width,i*interval,paint);
        }

    }
}
