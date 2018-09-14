package com.example.my360windowmanager.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Shuiyin {
    /**
     * 水印
     *
     * @param src       添加水印的图
     * @param watermark 水印图
     * @param alpha     水印的透明度
     * @return
     */
    public static Bitmap Watermark(Bitmap src, Bitmap watermark, int alpha) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        paint.setAntiAlias(true);
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);
        cv.drawBitmap(watermark, 0, 0, paint);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newb;
    }

  /* 添加文字到图片，类似水印文字。
   * * @param gContext
  * @param gResId
  * @param gText
  * @return
  * */
    public static Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale * 5));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        //int x = (bitmap.getWidth() - bounds.width()) / 2;
        //int y = (bitmap.getHeight() + bounds.height()) / 2;
        //draw  text  to the bottom
        int x = (bitmap.getWidth() - bounds.width()) / 10 * 9;
        int y = (bitmap.getHeight() + bounds.height()) / 10 * 9;
        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }
}
