/*
package com.example.myandroidcharts.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

*/
/**
 * Created by Administrator on 2017-09-12 0012.
 *//*

public class MUkeChart extends BaseView {


    Context context;

    public MUkeChart(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void drawColumn(Canvas canvas, Paint mPaint) {
        float cellWidth = width / axisDividedSizeX;
        for (int i = 0; i < columnInfo.length; i++) {
            mPaint.setColor(columnInfo[i][1]);
            float leftTopY = originaly - height * (columnInfo[i][0]) / axisDividedSizeY;
            canvas.drawRect(originalx + cellWidth * (i + 1), leftTopY, originalx + cellWidth * (i + 2), originaly, mPaint);
        }
    }

    @Override
    protected void drawYAxisScaleValue(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(16);
        mPaint.setFakeBoldText(true);
        float cellHeight = width / axisDividedSizeX;
        float cellValue = maxAxisValueY / axisDividedSizeY;
        for (int i = 0; i < axisDividedSizeX; i++) {
            canvas.drawText(cellValue++ + "", originalx - 30, originaly - cellHeight * i + 10, mPaint);

        }
    }

    @Override
    protected void drawYAxisScale(Canvas canvas, Paint mPaint) {
        float cellHeight = height / axisDividedSizeY;
        for (int i = 0; i < axisDividedSizeY - 1; i++) {
            canvas.drawLine(originalx, (originaly - cellHeight * (i + 1)), originalx + 10, (originaly - cellHeight * (i + 1)), mPaint);
        }
    }

    @Override
    protected void drawXAxisScaleValue(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(16);
        mPaint.setFakeBoldText(true);
        float cellWidth = width / axisDividedSizeX;
        for (int i = 0; i < axisDividedSizeX; i++) {
            canvas.drawText(i++ + "", cellWidth * i + originalx - 35, originaly + 30, mPaint);

        }
    }

    @Override
    protected void drawXAxisScale(Canvas canvas, Paint mPaint) {
        float cellWidth = width / axisDividedSizeX;
        for (int i = 0; i < axisDividedSizeX - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + originalx, originaly, cellWidth * (i + 1) + originalx, originaly - 10, mPaint);
        }

    }

    @Override
    protected void drawYAxis(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(originalx, originaly, originalx, originaly - height, mPaint);

    }

    @Override
    protected void drawXAxis(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(originalx, originaly, originalx + width, originaly, mPaint);

    }
}
*/
