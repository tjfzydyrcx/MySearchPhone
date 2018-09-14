package com.example.my360windowmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.my360windowmanager.Utils.Shuiyin;
import com.example.my360windowmanager.widget.PaintView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintViewActivity extends AppCompatActivity {
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_view);
        initView();
        initMenu();
    }

    /**
     * 初始化
     */
    private void initView() {
        paintView = (PaintView) findViewById(R.id.activity_paint_pv);
    }

    /**
     * 初始化底部菜单
     */
    private void initMenu() {
        //撤销
        menuItemSelected(R.id.activity_paint_undo, new MenuSelectedListener() {
            @Override
            public void onMenuSelected() {
                ToastUtils.show(PaintViewActivity.this, "撤销");
                paintView.undo();
            }
        });
        //恢复
        menuItemSelected(R.id.activity_paint_redo, new MenuSelectedListener() {
            @Override
            public void onMenuSelected() {
                ToastUtils.show(PaintViewActivity.this, "恢复");
                paintView.redo();
            }
        });

        //颜色
        menuItemSelected(R.id.activity_paint_color, new MenuSelectedListener() {
            @Override
            public void onMenuSelected() {
                ToastUtils.show(PaintViewActivity.this, "红色");
                paintView.setPaintColor(Color.RED);
                paintView.setEraserModel(false);
            }
        });
        //清空
        menuItemSelected(R.id.activity_paint_clear, new MenuSelectedListener() {
            @Override
            public void onMenuSelected() {
                ToastUtils.show(PaintViewActivity.this, "清空");
                paintView.clearAll();
            }
        });

        //橡皮擦
        menuItemSelected(R.id.activity_paint_eraser, new MenuSelectedListener() {
            @Override
            public void onMenuSelected() {
                ToastUtils.show(PaintViewActivity.this, "橡皮擦");
                paintView.setEraserModel(true);
            }
        });

        //保存
        menuItemSelected(R.id.activity_paint_save, new MenuSelectedListener() {
            @Override
            public void onMenuSelected() throws IOException {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "Ats";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                    ToastUtils.show(PaintViewActivity.this, "创建");
                }

                String imgName = "paint.jpg";
                ToastUtils.show(PaintViewActivity.this, "dianjile");
                if (paintView.saveImg(path, imgName)) {
                    ToastUtils.show(PaintViewActivity.this, "保存成功");
//                    shuiying(path+"/"+imgName);
                }
            }
        });
    }
    private void shuiying(String address){
        Bitmap bitmapsy= BitmapFactory.decodeResource(getResources(), R.mipmap.myshuiyingbg);
        Bitmap bitmap=BitmapFactory.decodeFile(address);
        Bitmap bitmap1=  Shuiyin.Watermark(bitmap, bitmapsy, 100);
        File f = new File(address);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 选中底部 Menu 菜单项
     */
    private void menuItemSelected(int viewId, final MenuSelectedListener listener) {
        findViewById(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onMenuSelected();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancel();
    }

    interface MenuSelectedListener {
        void onMenuSelected() throws IOException;
    }
}