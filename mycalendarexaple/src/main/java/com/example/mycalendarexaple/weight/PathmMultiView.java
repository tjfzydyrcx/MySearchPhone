package com.example.mycalendarexaple.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PathmMultiView extends View {
    private Paint paint;
    private Path path;
    private int width,height;
    //雷达图半径
    private float radius;
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    public PathmMultiView(Context context) {
        this(context,null);
    }
    public PathmMultiView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public PathmMultiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private void initPaint() {
    /*    width = 400;
        height = 400;*/
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
    }
/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,height);
    }*/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMultShape(canvas,6,radius);

    }
    /**
     * @param canvas 画布
     * @param count 绘制几边形
     * @param radius //外圆的半径
     */
    public void drawMultShape(Canvas canvas,int count,float radius){
        canvas.translate(radius,radius);//
        if(count<5){
            return;
        }
        for (int i=0;i<count;i++){
            if (i==0){
                path.moveTo(radius*cos(360/count*i),radius*sin(360/count*i));//绘制起点
            }else{
                path.lineTo(radius*cos(360/count*i),radius*sin(360/count*i));
            }
        }
        paint.setStrokeWidth(3);
        path.close();
        paint.setColor(Color.GREEN);
        canvas.drawPath(path,paint);
        //因为我下面不再绘制内容了 所以画布就不恢复了
    }
    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }
}