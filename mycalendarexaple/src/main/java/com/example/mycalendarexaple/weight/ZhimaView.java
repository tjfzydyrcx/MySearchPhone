package com.example.mycalendarexaple.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mycalendarexaple.R;

/**
 * 正N边形 * author : stone * email : aa86799@163.com * time : 2016/11/17 14 12
 */
public class ZhimaView extends View {
    private Paint mPaint;
    private float mR, mCx, mCy;
    private static final int mN =5;
    private static final float DEGREES_UNIT = 360 / mN; //正N边形每个角 360/mN能整除
    float radius;
    int mAlpha;
    private String[] titles = {"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质"};//标题
    private int[] icos = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};//五个维度的图标
    private float[] data = {170, 180, 100, 170, 150};//五个维度的数据值
    private float maxValue = 190;//每个维度的最大值
    private Paint mPaintText;//绘制文字的画笔
    private int radarMargin = 40;//

    public ZhimaView(Context context) {
        this(context, null);
    }

    public ZhimaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhimaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

  /*      float mW = getMeasuredWidth();
        float mH = getMeasuredHeight();
        mCx = mW / 2;
        mCy = mH / 2;
        radius = Math.min(mCx, mCy) / 4 * 3;
*/
        radius = Math.min(h, w) / 2 * 0.9f;
        mCx = w / 2;
        mCy = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorAccent));
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);//
        mPaint.setColor(Color.WHITE);//
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//
        radius =80;//星射线的初始值，也是最小的五边形的一条星射线的长度（后期会递增）
        mAlpha = 150;//白色五边形的透明度（后期后递减）
        for (int j = 0; j < 3; j++) {
            radius += 70;
            mAlpha -= 30;
            mPaint.setAlpha(mAlpha);
            float d = (float) (2 * radius * Math.sin(Math.toRadians(DEGREES_UNIT / 2)));
            float c = mCy - radius;
            float y = (d * d + mCy * mCy - c * c - radius * radius) / (2 * (mCy - c));
            float x = (float) (mCx + Math.sqrt(-1 * c * c + 2 * c * y + d * d - y * y));
            for (int i = 0; i < mN; i++) {
                canvas.save();
                canvas.rotate(DEGREES_UNIT * i, mCx, mCy);
                canvas.drawLine(mCx, mCy, mCx, c, mPaint);
                canvas.drawLine(mCx, c, x, y, mPaint);
                canvas.restore();
            }
        }
    }


}
