package com.example.mycalendarexaple.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-09-08 0008.
 */
public class CreditScoreView extends View {

    //数据个数
    private int dataCount = 6;
    //每个角的弧度
    private float radian = (float) (Math.PI * 2 / dataCount);
    //雷达图半径
    private float radius;
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    // 各维度标题
    private String[] titles = {""};
    //各维度图标
    private int[] icons = {};

    //各维度分值
    private float[] data = {170, 180, 160, 170, 180};

    //数据最大值
    private float maxValue = 180;
    //雷达图与标题的间距、
    private int radarMargin = DensityUtils.dp2px(getContext(), 15);

    //雷达区画笔
    private Paint mainPaint;
    //数据区画笔
    private Paint valuePaint;
    //分数画笔
    private Paint scorePaint;
    //标题画笔
    private Paint titlePaint;
    //图标画笔
    private Paint iconPaint;


    //分数大小
    private int scoreSize = DensityUtils.dp2px(getContext(), 28);
    //标题文字大小
    private int titleSize = DensityUtils.dp2px(getContext(), 13);


    public CreditScoreView(Context context) {
        super(context);
        init();
    }

    public CreditScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mainPaint = new Paint();//初始化白色五边形的画笔
        mainPaint.setAntiAlias(true);//
        mainPaint.setStrokeWidth(5);//
        mainPaint.setColor(Color.BLACK);//
        mainPaint.setStyle(Paint.Style.STROKE);//
        radius = 80;//星射线的初始值，也是最小的五边形的一条星射线的长度（后期会递增）
    }

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

        drawPolygon(canvas);
    }

    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(radian * i));
            float y = (float) (centerY + radius * Math.sin(radian * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制正多边形
     */
    private void drawPolygon(Canvas canvas) {

        Path path = new Path();
        path = new Path();//初始化红色五边形路径
        int mAlpha = 240;//白色五边形的透明度（后期后递减）
        float r = radius / (dataCount - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i < dataCount; i++) {//中心点不用绘制
            radius += 70;//每一层五边形的星射线增加 70 的长度
//            mAlpha -= 30;//每一层五边形的透明度减少 30
//            mainPaint.setAlpha(mAlpha);
            float curR = r * i;//当前半径
            path.reset();

            for (int j = 0; j < dataCount; j++) {
                //根据半径，计算出蜘蛛丝上每个点的坐标
                float x = (float) (centerX + curR * Math.cos(radian * j));
                float y = (float) (centerY + curR * Math.sin(radian * j));
                if (j == 0) {
                    path.moveTo(x, y);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                   /* float x = (float) (centerX + curR * Math.cos(radian * j));
                    float y = (float) (centerY + curR * Math.sin(radian * j));*/
                    path.lineTo(x, y);
                }

            }
            path.close();//闭合路径
            canvas.drawPath(path, mainPaint);

        }
      /*  mainPaint.setColor(Color.WHITE);//设置颜色为白色
        mainPaint.setStrokeWidth(2);//设置宽度为2*/
     /*   for (int i = 0; i < dataCount; i++) {
            float x = (float) (centerX + radius * Math.cos(radian * i));
            float y = (float) (centerY + radius * Math.sin(radian * i));
            canvas.drawLine(centerX, centerY, x, y, mainPaint);//绘制

        }*/

    }
  /*  private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {

            }
        }
    }*/

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position    坐标位置
     * @param radarMargin 雷达图与维度标题的间距
     * @param percent     覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;

        if (position == 0) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 1) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 2) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 3) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 4) {// Y 轴正方向顶点的计算
            x = centerX;
            y = (int) (centerY - (radius + radarMargin) * percent);
        }
        return new Point(x, y);

    }

}
