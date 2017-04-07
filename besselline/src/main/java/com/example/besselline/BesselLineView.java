package com.example.besselline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suhu on 2017/4/6.
 */

public class BesselLineView extends View{
    private float spacingX,spacingY,maxY,minY,width,height,marginX;
    private float marginY = 40;

    private int line_width = 5;
    private int point_number = 5;

    private List<Bean> list;
    private List<Float> xPoint,yPoint;

    private Paint paint;


    public BesselLineView(Context context) {
        super(context);
    }

    public BesselLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.BesselLineView);
        width = array.getFloat(R.styleable.BesselLineView_widths,400);
        height = array.getFloat(R.styleable.BesselLineView_heights,200);
        xPoint = new ArrayList<>();
        yPoint = new ArrayList<>();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        xPoint.clear();
        yPoint.clear();

        if (list!=null && list.size()!=0){
            if (getWidth()>0&& width>getWidth()){
                width = getWidth();
            }
            marginX = (getWidth()-width)/2;
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(line_width);


            //画x坐标
            canvas.drawLine(marginX,height+marginY,marginX+width,height+marginY,paint);
            //画y坐标
            canvas.drawLine(marginX,marginY,marginX,height+marginY+line_width/2,paint);
            //画三角
            Path path = new Path();
            path.moveTo(marginX+width,height+marginY-10);
            path.lineTo(marginX+width,height+marginY+10);
            path.lineTo(marginX+width+35,height+marginY);
            path.close();
            canvas.drawPath(path,paint);

            path.moveTo(marginX-10,marginY);
            path.lineTo(marginX+10,marginY);
            path.lineTo(marginX,marginY-35);
            path.close();
            canvas.drawPath(path,paint);

            //画坐标
            spacingX = width/(list.size()+1);
            spacingY = getGetSpacingY(list);

            //x坐标轴
            float textSize = paint.measureText(list.get(0).getKey()+"");
            paint.setTextSize(30);
            for (int i = 0; i < list.size(); i++) {
                paint.setColor(Color.WHITE);
                canvas.drawCircle(marginX+(i+1)*spacingX,marginY+height,2,paint);
                paint.setColor(Color.BLACK);
                canvas.drawText(list.get(i).getKey(),marginX+(i+1)*spacingX-textSize/2,marginY+height+30,paint);
            }

            //y坐标轴
            float y = height/(point_number+1);
            for (int i = 0; i < point_number; i++) {
                paint.setColor(Color.WHITE);
                canvas.drawCircle(marginX,marginY+(i+1)*y,2,paint);
                paint.setColor(Color.BLACK);
                int text =(int) (minY+i*spacingY);
                canvas.drawText(text+"",marginX-60,marginY+height-(i)*y+15,paint);
            }
            canvas.drawText((int)maxY+"",marginX-60,marginY+height-(5)*y+15,paint);

            //画点
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            for (int i = 0; i < list.size(); i++) {
                float drawY = height+marginY-(height*list.get(i).getValue()*5)/(6*maxY);
                canvas.drawPoint(marginX+(i+1)*spacingX,drawY,paint);
                xPoint.add(marginX+(i+1)*spacingX);
                yPoint.add(drawY);
            }

            //绘制贝塞尔曲线（将点连接成线）
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLUE);
            if (xPoint!=null&& yPoint!=null){
                for (int i = 0; i < xPoint.size(); i++) {
                    if (i==0){
                        //第一条为二阶贝塞尔
                        path.moveTo(xPoint.get(i),yPoint.get(i));
                    }else {
                        //三阶贝塞尔
                        path.cubicTo(
                                (xPoint.get(i-1)+xPoint.get(i))/2, yPoint.get(i-1),//第一个控制点
                                (xPoint.get(i-1)+xPoint.get(i))/2, yPoint.get(i), //第二个控制点
                                xPoint.get(i),yPoint.get(i)                      //该点的坐标
                                );
                    }

                }
                //画虚线
//                PathEffect pathEffect=new DashPathEffect(new float[]{10,10,10,10},1);
//                paint.setPathEffect(pathEffect);
                canvas.drawPath(path,paint);
            }
        }

    }


    private float getGetSpacingY(List<Bean> list){
        if (list!=null && list.size()!=0){
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getValue()>maxY){
                    maxY = list.get(i).getValue();
                }
                if (list.get(i).getValue()<minY){
                    minY = list.get(i).getValue();
                }
            }
            return  (maxY-minY)/(point_number);
        }
        return 0;
    }

    public void setList(List<Bean> list) {
        this.list = list;
        //通知绘画
        invalidate();
    }



}
