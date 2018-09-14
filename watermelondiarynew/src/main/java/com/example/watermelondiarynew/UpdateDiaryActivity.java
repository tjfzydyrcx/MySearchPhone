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
public class UpdateDiaryActivity extends AppCompatActivity {

    @BindView(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @BindView(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @BindView(R.id.update_diary_et_content)
    LinedEditText mUpdateDiaryEtContent;
    @BindView(R.id.update_diary_fab_back)
    FloatingActionButton mUpdateDiaryFabBack;
    @BindView(R.id.update_diary_fab_add)
    FloatingActionButton mUpdateDiaryFabAdd;
    @BindView(R.id.update_diary_fab_delete)
    FloatingActionButton mUpdateDiaryFabDelete;
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
    @BindView(R.id.update_diary_tv_tag)
    TextView mTvTag;
    private DiaryDatabaseHelper mHelper;


    public static void startActivity(Context context, String title, String content, String tag) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        initTitle();
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        StatusBarCompat.compat(this, Color.parseColor("#161414"));
        Intent intent = getIntent();
        mUpdateDiaryTvDate.setText("今天，" + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
        mTvTag.setText(intent.getStringExtra("tag"));

    }

    private void initTitle() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mCommonTvTitle.setText("修改日记");
    }


    @OnClick({R.id.common_iv_back, R.id.update_diary_tv_date, R.id.update_diary_et_title, R.id.update_diary_et_content, R.id.update_diary_fab_back, R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            case R.id.update_diary_tv_date:
                break;
            case R.id.update_diary_et_title:
                break;
            case R.id.update_diary_et_content:
                break;
            case R.id.update_diary_fab_back:
                MainActivity.startActivity(this);
                finish();
                break;
            case R.id.update_diary_fab_add:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.update_diary_fab_delete:

                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("确定要删除该日记吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tag = mTvTag.getText().toString();
                        SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                        dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                        Intent intent = new Intent(UpdateDiaryActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
