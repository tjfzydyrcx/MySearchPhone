package com.example.mycalendarexaple.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017-09-02 0002.
 */
public class PaintView extends View {
    //画笔
    private Paint mPaint;
    private Path mPath;
    //橡皮擦
    private Path eraserPath;
    private Paint eraserPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private float mLastX, mLastY;//上次的坐标
    private Paint mBitmapPaint;
    //使用LinkedList 模拟栈，来保存 Path
    private LinkedList<PathBean> undoList;
    private LinkedList<PathBean> redoList;
    private boolean isEraserModel;

    public PaintView(Context context) {
        super(context);

    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

        // 设置背景色
        setBackgroundColor(Color.WHITE);
        //设置层类型
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(4f);//设置画笔宽度
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);//使画笔更加圆润
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //图片画笔
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        post(new Runnable() {
            //拿到控件的宽高
            @Override
            public void run() {
                //由于橡皮擦使用的是Color.TRANSPARENT,不能用RGB-565
                mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
                mCanvas = new Canvas(mBitmap);
                //抗锯齿
                mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
                // 背景色
                mCanvas.drawColor(Color.WHITE);
            }
        });
        undoList = new LinkedList<>();
        redoList = new LinkedList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            // 将mBitmap 绘制在画布上，最终显示
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            if (!isEraserModel) {
                if (null != mPath) {
                    //显示实时正在绘制的path轨迹
                    canvas.drawPath(mPath, mPaint);
                }
            } else {
                if (null != eraserPath) {
                    canvas.drawPath(eraserPath, eraserPaint);
                }
            }
        }
    }

    /**
     * 撤销操作
     */
    public void undo() {
        if (!undoList.isEmpty()) {
            clearPaint();//清除之前绘制的内容
            PathBean lastPb = undoList.removeLast();//将最后一个移除
            redoList.add(lastPb);//加入到恢复集合中
            //遍历，将Path重新绘制到 mCanvas
            for (PathBean pb : undoList) {
                mCanvas.drawPath(pb.path, pb.paint);
            }
            invalidate();
        }
    }

    /**
     * 恢复操作
     */
    public void redo() {
        if (!redoList.isEmpty()) {
            PathBean pathBean = redoList.removeLast();
            mCanvas.drawPath(pathBean.path, pathBean.paint);
            invalidate();
            undoList.add(pathBean);
        }
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setPaintColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    /**
     * 清空 包括撤销和恢复操作
     */
    public void clearAll() {
        clearPaint();
        mLastY = 0f;
        redoList.clear();
        undoList.clear();
    }

    /**
     * 设置橡皮擦模式
     *
     * @param isEraserModle
     */
    public void setEraserModel(boolean isEraserModle) {
        this.isEraserModel = isEraserModle;
        if (eraserPaint == null) {
            eraserPaint = new Paint(mPaint);
            eraserPaint.setStrokeWidth(15f);
            eraserPaint.setColor(Color.TRANSPARENT);
            eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        }
    }

    /**
     * 保存到指定的文件
     *
     * @param filePath
     * @param imgName
     * @return
     */
    public boolean saveImg(String filePath, String imgName) {
        boolean isCanSave = mBitmap != null && mLastY != 0f && !undoList.isEmpty();
        if (isCanSave) {
            File file = new File(filePath + File.separator + imgName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                if (mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                    fos.flush();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeStream(fos);
            }
        }
        return false;
    }

    /**
     * 是否可以撤销
     */
    public boolean isCanUndo() {
        return undoList.isEmpty();
    }

    /**
     * 是否可以恢复
     */
    public boolean isCanRedo() {
        return redoList.isEmpty();
    }

    /**
     * 清除绘制内容
     * 直接绘制白色背景
     */
    private void clearPaint() {
        mCanvas.drawColor(Color.WHITE);
        invalidate();
    }


    /**
     * 触摸事件 触摸绘制
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEraserModel) {
            commonTouchEvent(event);
        } else {
            eraserTouchEvent(event);
        }
        invalidate();
        return true;
    }

    /**
     * 橡皮擦事件
     */
    private void eraserTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //路径
                eraserPath = new Path();
                mLastX = x;
                mLastY = y;
                eraserPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                if (dx >= 3 || dy >= 3) {//绘制的最小距离 3px
                    eraserPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(eraserPath, eraserPaint);//将路径绘制在mBitmap上
                eraserPath.reset();
                eraserPath = null;
                break;
        }
    }

    /**
     * 普通画笔事件
     */
    private void commonTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //路径
                mPath = new Path();
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                if (dx >= 3 || dy >= 3) {//绘制的最小距离 3px
                    //利用二阶贝塞尔曲线，使绘制路径更加圆滑
                    mPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);//将路径绘制在mBitmap上
                Path path = new Path(mPath);//复制出一份mPath
                Paint paint = new Paint(mPaint);
                PathBean pb = new PathBean(path, paint);
                undoList.add(pb);//将路径对象存入集合
                mPath.reset();
                mPath = null;
                break;
        }
    }

    /**
     * 关闭流
     */
    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 200);
        }
    }

    /**
     * 路径对象
     */
    class PathBean {
        Path path;
        Paint paint;

        PathBean(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }


}
