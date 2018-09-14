package com.example.myandroidcharts.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-09-12 0012.
 */
public class zuobiao extends View {
    int mWidth, mHeight;
    Paint mPaint;

    public zuobiao(Context context) {
        super(context);
        init();

    }

    public zuobiao(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public zuobiao(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w / 2;
        mHeight = h / 2;

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawLine(100, 400, 100 + getWidth(), 400, mPaint);
        canvas.drawLine(100, 400,100, 400 - getHeight(), mPaint);
    }
}
