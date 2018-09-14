package com.example.watermelondiarynew;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.watermelondiarynew.db.DiaryDatabaseHelper;
import com.example.watermelondiarynew.utils.AppManager;
import com.example.watermelondiarynew.utils.GetDate;
import com.example.watermelondiarynew.utils.StatusBarCompat;
import com.example.watermelondiarynew.weight.LinedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by Administrator on 2017-09-07 0007.
 */
public class AddDiaryActivity extends AppCompatActivity {

    @BindView(R.id.add_diary_tv_date)
    TextView mAddDiaryTvDate;
    @BindView(R.id.add_diary_et_title)
    EditText mAddDiaryEtTitle;
    @BindView(R.id.add_diary_et_content)
    LinedEditText mAddDiaryEtContent;
    @BindView(R.id.add_diary_fab_back)
    FloatingActionButton mAddDiaryFabBack;
    @BindView(R.id.add_diary_fab_add)
    FloatingActionButton mAddDiaryFabAdd;

    @BindView(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @BindView(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @BindView(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @BindView(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @BindView(R.id.common_iv_test)
    ImageView mCommonIvTest;

    private DiaryDatabaseHelper mHelper;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        StatusBarCompat.compat(this, Color.parseColor("#161414"));
        mCommonTvTitle.setText("添加日记");
        mAddDiaryTvDate.setText("今天，" + GetDate.getDate());
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);

    }

    @OnClick({R.id.common_iv_back, R.id.add_diary_et_title, R.id.add_diary_et_content, R.id.add_diary_fab_back, R.id.add_diary_fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.add_diary_et_title:
                break;

            case R.id.add_diary_fab_back:

                String date = GetDate.getDate().toString();
                String tag = String.valueOf(System.currentTimeMillis());
                String title = mAddDiaryEtTitle.getText().toString();
                String content = mAddDiaryEtContent.getText().toString();
                if (!title.equals("") || !content.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", date);
                    values.put("title", title);
                    values.put("content", content);
                    values.put("tag", tag);
                    db.insert("Diary", null, values);
                    values.clear();
                }
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.add_diary_fab_add:
                final String dateBack = GetDate.getDate().toString();
                final String titleBack = mAddDiaryEtTitle.getText().toString();
                final String contentBack = mAddDiaryEtContent.getText().toString();
                if (!titleBack.isEmpty() || !contentBack.isEmpty()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("是否保存日记内容？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("date", dateBack);
                            values.put("title", titleBack);
                            values.put("content", contentBack);
                            db.insert("Diary", null, values);
                            values.clear();
                            Intent intent = new Intent(AddDiaryActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AddDiaryActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
                } else {
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
