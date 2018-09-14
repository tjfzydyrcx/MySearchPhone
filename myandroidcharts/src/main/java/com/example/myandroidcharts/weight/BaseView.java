package com.example.myandroidcharts.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.myandroidcharts.R;


public abstract class BaseView extends View {

    //第一个维度为值，第二个纬度为颜色
    public int[][] columnInfo;
    public int axisDividedSizeX;//X分几等份
    public int axisDividedSizeY;//Y分等分
    public float maxAxisValueY;//y轴最大值
    public float maxAxisValueX;//X轴最大值
    //图表私有属性
    private String mGraphTitle;
    private String mXAxisName;
    private String mYAxisName;
    private float mAxisTextSize;
    private int mAxisTextColor;
    //画笔
    private Paint mPaint;
    private Context mContext;
    /**
     * 起点的X,Y坐标值
     */

    public int originalx = 100;
    public int originaly = 800;
    /**
     * 视图的宽高
     **/

    public int width;
    public int height;

    public BaseView(Context context) {
        super(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs, -1);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGraphStyle);
        mGraphTitle = typedArray.getString(R.styleable.MyGraphStyle_graphTitle);
        mXAxisName = typedArray.getString(R.styleable.MyGraphStyle_xAxisName);
        mYAxisName = typedArray.getString(R.styleable.MyGraphStyle_YAxisName);
        mAxisTextSize = typedArray.getDimension(R.styleable.MyGraphStyle_textSise, 12);
        mAxisTextColor = typedArray.getColor(R.styleable.MyGraphStyle_textColor, context.getResources().getColor(android.R.color.black));

        if (typedArray != null) {
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth() - originalx;
        height = (originaly > getHeight() ? getHeight() : originaly) - 400;
        drawXAxis(canvas, mPaint);//X轴
        drawYAxis(canvas, mPaint);//Y轴
        drawTitle(canvas, mPaint);//标题
        drawXAxisScale(canvas, mPaint);//X轴刻度
        drawXAxisScaleValue(canvas, mPaint);//X轴刻度值
        drawYAxisScale(canvas, mPaint);//Y轴
        drawYAxisScaleValue(canvas, mPaint);//Y轴刻度值
        drawXAxisArrow(canvas, mPaint);//X轴箭头
        drawYAxisArrow(canvas, mPaint);//Y轴箭头
        drawColumn(canvas, mPaint);//画柱
    }


    /**
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawColumn(Canvas canvas, Paint mPaint);

    private void drawYAxisArrow(Canvas canvas, Paint mPaint) {
        Path mPathY = new Path();
        mPathY.moveTo(originalx, originaly - height - 30);
        mPathY.lineTo(originalx - 10, originaly - height);
        mPathY.lineTo(originalx + 10, originaly - height);
        mPathY.close();
        canvas.drawPath(mPathY, mPaint);
        canvas.drawText(mYAxisName, originalx - 50, originaly - height - 30, mPaint);

    }

    /**
     * @param canvas
     * @param mPaint
     */
    private void drawXAxisArrow(Canvas canvas, Paint mPaint) {
        Path mPathX = new Path();
        mPathX.moveTo(originalx + width + 30, originaly);
        mPathX.lineTo(originalx + width, originaly + 10);
        mPathX.lineTo(originalx + width, originaly - 10);
        mPathX.close();
        canvas.drawPath(mPathX, mPaint);
        canvas.drawText(mXAxisName, originalx + width, originaly + 50, mPaint);
    }


    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint mPaint);


    protected abstract void drawYAxisScale(Canvas canvas, Paint mPaint);


    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint mPaint);


    protected abstract void drawXAxisScale(Canvas canvas, Paint mPaint);


    private void drawTitle(Canvas canvas, Paint mPaint) {
        if (!TextUtils.isEmpty(mGraphTitle)) {
            mPaint.setTextSize(mAxisTextSize);
            mPaint.setColor(mAxisTextColor);
            mPaint.setFakeBoldText(true);
            canvas.drawText(mGraphTitle, (getWidth() / 2) - (mPaint.measureText(mGraphTitle)) / 2, originaly + 40, mPaint);
        }

    }


    protected abstract void drawYAxis(Canvas canvas, Paint mPaint);


    protected abstract void drawXAxis(Canvas canvas, Paint mPaint);


    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    public void setAxisDividedSizeY(int axisDividedSizeY) {
        this.axisDividedSizeY = axisDividedSizeY;
    }

    public void setAxisDividedSizeX(int axisDividedSizeX) {
        this.axisDividedSizeX = axisDividedSizeX;
    }

    public void setMaxAxisValueY(float maxAxisValueY) {
        this.maxAxisValueY = maxAxisValueY;
    }

    public void setMaxAxisValueX(float maxAxisValueX) {
        this.maxAxisValueX = maxAxisValueX;
    }
}
