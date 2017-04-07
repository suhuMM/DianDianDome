package com.example.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by suhu on 2017/3/31.
 */

public class ChessView extends View{

    private int width,height;
    private float size = 120;
    private float radius = 40;
    private Paint paint;
    private float startX,startY;
    private String text [] = {"楚","河","汉","界"};
    private String chessText[] = {"车","马","象","士","将","士","象","马","车"};


    public ChessView(Context context) {
        super(context);
    }

    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);

        width = getWidth();
        height = getHeight();
        startX = (width-8*size)/2;
        startY = (width-8*size)/2;

        //画横线 x固定 y变化
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(startX,startY+i*size,width-startX,startY+i*size,paint);
        }

        //画竖线 y固定 x 变化
        for (int i = 0; i < 9; i++) {
            if (i==0 || i==8){
                canvas.drawLine(startY+size*i,startY,startY+size*i,startY+size*9,paint);
            }else {
                canvas.drawLine(startY+size*i,startY,startY+size*i,startY+size*4,paint);
                canvas.drawLine(startY+size*i,startY+size*5,startY+size*i,startY+size*9,paint);
            }
        }
        canvas.drawLine(startX+3*size,startY,startX+5*size,startY+2*size,paint);
        canvas.drawLine(startX+5*size,startY,startX+3*size,startY+2*size,paint);
        canvas.drawLine(startX+3*size,startY+7*size,startX+5*size,startY+9*size,paint);
        canvas.drawLine(startX+3*size,startY+9*size,startX+5*size,startY+7*size,paint);

        paint.setTextSize(60);
        float middleLine = startY+4*size+size/2;
        float textSize = paint.measureText(text[0]);
        Paint.FontMetrics fm = paint.getFontMetrics();
        float baseLine = middleLine-(fm.ascent+fm.descent)/2;
        for (int i = 0; i < text.length; i++) {
            int j;
            if (i>1){
                j = 3;
            }else {
                j = 1;
            }
            float start = startX+(i+j)*size+(size-textSize)/2;
            canvas.drawText(text[i],start,baseLine,paint);
        }

        paint.setTextSize(60);
        float chessMiddleLine = startY+9*size;
        float chessTextSize = paint.measureText(chessText[0]);
        float chessBaseLine = chessMiddleLine-(fm.ascent+fm.descent)/2;

        for (int i = 0; i < chessText.length; i++) {
            paint.setColor(Color.rgb(150,150,150));
            canvas.drawCircle(startX+i*size,startY+9*size,radius,paint);
            float start = startX+i*size-chessTextSize/2;
            paint.setColor(Color.RED);
            canvas.drawText(chessText[i],start,chessBaseLine,paint);
        }

        chessBaseLine = startY+6*size-(fm.ascent+fm.descent)/2;
        for (int i = 0; i < 5; i++) {
            paint.setColor(Color.rgb(150,150,150));
            canvas.drawCircle(startX+i*2*size,startY+6*size,radius,paint);
            float start = startX+i*2*size-chessTextSize/2;
            paint.setColor(Color.RED);
            canvas.drawText("兵",start,chessBaseLine,paint);
        }
        chessBaseLine = startY+7*size-(fm.ascent+fm.descent)/2;
        paint.setColor(Color.rgb(150,150,150));
        canvas.drawCircle(startX+size,startY+7*size,radius,paint);
        canvas.drawCircle(startX+7*size,startY+7*size,radius,paint);

        paint.setColor(Color.RED);
        float start = startX+size-chessTextSize/2;
        canvas.drawText("炮",start,chessBaseLine,paint);
        start = startX+7*size-chessTextSize/2;
        canvas.drawText("炮",start,chessBaseLine,paint);

        //paint.setTextSkewX((float) Math.PI/2);
        paint.setTextSize(150);
        String name = "中国象棋";
        float length  = paint.measureText(name);
        start = (width-length)/2;
        baseLine = startY+11*size-(fm.ascent+fm.descent)/2;
        canvas.drawText(name,start,baseLine,paint);

    }
}
