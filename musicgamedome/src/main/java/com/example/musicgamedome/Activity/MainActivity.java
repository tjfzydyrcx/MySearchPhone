package com.example.musicgamedome.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicgamedome.Model.IAlerDialogButtonListener;
import com.example.musicgamedome.Model.IWordButtonClickListener;
import com.example.musicgamedome.Model.Song;
import com.example.musicgamedome.Model.WordButton;
import com.example.musicgamedome.MyView.MyGridView;
import com.example.musicgamedome.R;
import com.example.musicgamedome.Utils.Dialogutils;
import com.example.musicgamedome.Utils.FileSaveData;
import com.example.musicgamedome.Utils.Getlayout;
import com.example.musicgamedome.Utils.IntentActivity;
import com.example.musicgamedome.Utils.LogUtils;
import com.example.musicgamedome.Utils.Myplay;
import com.example.musicgamedome.Utils.RandCharUtils;
import com.example.musicgamedome.data.Const;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, IWordButtonClickListener {
    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 答案状态 正确
     */
    public static final int STATUS_ANSWER_RIGHT = 1;
    /**
     * 答案状态 错误
     */
    public static final int STATUS_ANSWER_WRONG = 2;
    /**
     * 答案状态 不完整
     */

    public static final int STATUS_ANSWER_LACK = 3;

    //闪烁的次数
    public static final int SPARD_TIME = 6;

    public static final int ID_DIALOG_DELETE_WORD = 1;

    public static final int ID_DIALOG_TIP_ANSWER = 2;
    public static final int ID_DIALOG_LACK_COINS = 3;
    //唱片相关动画
    private Animation mPanAnim;
    private LinearInterpolator mPanLin;
    //
    private Animation mBarInAnim;
    private LinearInterpolator mBarInLin;
    //
    private Animation mBarOutAnim;
    private LinearInterpolator mBarOutLin;
    //按键控件
    private ImageButton mBtnPlayStart;
    //唱片和拨杆控件
    private ImageView mViewPan, mViewPanBar;
    //当前动画是否正在运行
    private boolean misRunning = false;
    ArrayList<WordButton> mAllWords;

    MyGridView myGridView;
    ArrayList<WordButton> mBtnSelectWords;
    //已选择文字框容器
    private LinearLayout mViewWordsContainer;
    //当前的歌曲
    private Song mCurrentSong;
    //当前的索引
    private int mCurrentStageIndex = -1;
    //过关界面
    View mPassView;
    //当前金币数量
    private int mCurrentCoins = Const.TOTAL_COINS;

    //金币View
    private TextView mViewCurrentCoins;
    private TextView mViewCurrentStagePass;//当前第几关卡
    private TextView mCurrentStageView;
    private TextView mCurrentSongNamePassView;//过关歌曲名


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int datas[] = FileSaveData.loadData(MainActivity.this);
        mCurrentStageIndex = datas[Const.INDEX_LOAD_DATA_STAGE];
        mCurrentCoins = datas[Const.INDEX_LOAD_DATA_COINS];

        LogUtils.e("数据====", mCurrentStageIndex + "===" + mCurrentCoins);
        mBtnPlayStart = $(R.id.btn_play_start);
        mBtnPlayStart.setVisibility(View.VISIBLE);
        mViewPan = $(R.id.imageView2);
        mViewPanBar = $(R.id.imageView4);
        myGridView = $(R.id.myGridview);
        mViewWordsContainer = $(R.id.select_layout);
        mViewCurrentCoins = $(R.id.txt_bar_coins);
        myGridView.registOnWordButtonClick(this);
        mBtnPlayStart.setOnClickListener(this);
        //唱片动画设置及监听
        mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mPanLin = new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        mPanAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //拨杆返回动画
                mViewPanBar.startAnimation(mBarOutAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //拨杆进入动画设置及监听
        mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.bar_in);
        mBarInLin = new LinearInterpolator();
        mBarInAnim.setInterpolator(mBarInLin);
        mBarInAnim.setFillAfter(true);
        mBarInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 开始唱片动画
                mViewPan.startAnimation(mPanAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //拨杆返回动画设置及监听
        mBarOutAnim = AnimationUtils.loadAnimation(this, R.anim.bar_out);
        mBarOutLin = new LinearInterpolator();
        mBarOutAnim.setInterpolator(mBarOutLin);
        mBarOutAnim.setFillAfter(true);
        mBarOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 整套动画播放完毕
                Log.e("tese", "整套动画播放完毕");
                misRunning = false;
                mBtnPlayStart.setVisibility(View.VISIBLE);
                Myplay.stopThesong(MainActivity.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //初始化游戏数据
        initCurrentStageData();
        //处理删除按钮事件
        handledeleteWord();
        //处理提示按键事件
        handleTipAnswer();

    }

    /**
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_start:
                Log.e("AA", "SS");

                handlePlayButton();
                break;
        }
    }

    /**
     * 处理圆盘中的播放按钮，开始播放音乐
     */
    private void handlePlayButton() {
        if (mViewPanBar != null) {
            if (!misRunning) {
                misRunning = true;
                //拨杆开始动画
                mViewPanBar.startAnimation(mBarInAnim);
                mBtnPlayStart.setVisibility(View.INVISIBLE);
                Myplay.playSong(MainActivity.this, mCurrentSong.getSongFileMame());
            }
        }
    }

    @Override
    public void onPause() {
        mViewPan.clearAnimation();
        Myplay.stopThesong(MainActivity.this);
        FileSaveData.SaveData(MainActivity.this, mCurrentStageIndex - 1, mCurrentCoins);
        super.onPause();
    }

    public Song loadStageSongInfo(int index) {
        Song song = new Song();
        String[] stage = Const.SONG_INFO[index];
        song.setSongFileMame(stage[Const.INDEX_FILE_NAME]);
        song.setSongName(stage[Const.INDEX_SONG_NAME]);
        return song;
    }

    /**
     * 加载当前关的信息
     * 读取当前关的歌曲信息
     * 初始化已选择框
     */
    private void initCurrentStageData() {
        mViewCurrentCoins.setText(mCurrentCoins + "");
        //读取当前关的歌曲信息
        mCurrentSong = loadStageSongInfo(++mCurrentStageIndex);
        //

        //初始化已选择框
        mBtnSelectWords = initWordSelect();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        //清除原来的数据
        mViewWordsContainer.removeAllViews();

        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton, params);
        }
        //显示当前的索引
        mCurrentStageView = $(R.id.text_current_stage);
        if (mCurrentStageView != null) {
            mCurrentStageView.setText(mCurrentStageIndex + 1 + "");
        }
        //获得数据
        mAllWords = initAllWord();

        //更新数据  MyGridView
        myGridView.updateData(mAllWords);
        //一开始就进行播放
        handlePlayButton();

    }

    /**
     * 初始化待选文字框
     *
     * @return
     */
    private ArrayList<WordButton> initAllWord() {
        ArrayList<WordButton> data = new ArrayList<>();
        //获得所有待选文字
        String[] words = generateWords();

        for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
            WordButton button = new WordButton();
            button.mWordString = words[i];
            data.add(button);
        }
        return data;
    }

    /**
     * 待选框中的随机汉字
     */
    private String[] generateWords() {
        Random random = new Random();

        String[] words = new String[MyGridView.COUNTS_WORDS];
        //存入歌名
        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
            words[i] = mCurrentSong.getNameCharacters()[i] + "";
        }
        //获取随机文字
        for (int b = mCurrentSong.getNameLength(); b < MyGridView.COUNTS_WORDS; b++) {
            words[b] = RandCharUtils.getRandomChar() + "";
        }
        //打乱文字顺序  首先从所有的元素中随机选取一个与第一个元素进行交换
        //然后在第二个之后选择一个元素与第二个交换，直到最后一个元素
        //这样能够保证每个元素在每个位置的概率都是1/n;
        for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            String buf = words[index];
            words[index] = words[i];
            words[i] = buf;
        }
        return words;
    }

    /**
     * 初始化已选文字框
     */
    private ArrayList<WordButton> initWordSelect() {
        ArrayList<WordButton> data = new ArrayList<>();
        for (int b = 0; b < mCurrentSong.getNameLength(); b++) {
            View view = Getlayout.getView(MainActivity.this, R.layout.self_ui_fridview_item);
            final WordButton holder = new WordButton();
            holder.mViewButton = view.findViewById(R.id.item_btn);
            holder.mViewButton.setText("");
            holder.mIsVisisable = false;
            holder.mViewButton.setTextColor(Color.WHITE);
            holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
            holder.mViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearTheAnswer(holder);
                }
            });
            data.add(holder);
        }
        return data;
    }

    /**
     * 点击事件
     */

    @Override
    public void onWordButtonClick(WordButton wordButton) {
        Log.e("wode==", wordButton.mIndex + "--===" + wordButton.mWordString);
        setSelectWord(wordButton);
        //对答案的检查
        int checkResult = cheackTheAnswer();
        if (checkResult == STATUS_ANSWER_RIGHT) {
            //  过关
            handlePassEvent();
        } else if (checkResult == STATUS_ANSWER_WRONG) {
            // 失败提示  闪烁文字并提示用户
            sparkTheWords();
        } else if (checkResult == STATUS_ANSWER_LACK) {
            //设置白颜色
            for (int i = 0; i < mBtnSelectWords.size(); i++) {
                mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
            }
        }

    }

    /**
     * 处理过关界面及事件
     */
    private void handlePassEvent() {
        LogUtils.e("zhi", "zhixing");

        mPassView = $(R.id.pass_view);
        mPassView.setVisibility(View.VISIBLE);


        //停掉未完成的动画
        mViewPan.clearAnimation();
        //停止音乐
        Myplay.stopThesong(MainActivity.this);
        Myplay.playTone(MainActivity.this, Myplay.INDEX_STONE_COIN);
        //当前关的索引
        mViewCurrentStagePass = $(R.id.text_current_stage_pass);
        if (mViewCurrentStagePass != null) {
            mViewCurrentStagePass.setText(mCurrentStageIndex + 1 + "");
        }
        //显示歌曲名称
        mCurrentSongNamePassView = $(R.id.text_current_song_name_pass);
        if (mCurrentSongNamePassView != null) {
            mCurrentSongNamePassView.setText(mCurrentSong.getSongName() + "");
        }
        //下一关的按键处理
        ImageButton btnPass = $(R.id.btn_next);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 是否有下一关 或者是已经通关
                if (judegAppPassed()) {
                    //进入到通关界面
                    IntentActivity.startActivity(MainActivity.this, AllPassView.class);

                } else {
                    //开始下一关
                    mPassView.setVisibility(View.GONE);
                    mCurrentCoins = mCurrentCoins + 3;
                    FileSaveData.SaveData(MainActivity.this, mCurrentStageIndex - 1, mCurrentCoins);

                    //加载关卡数据
                    initCurrentStageData();

                }
            }
        });
    }

    /**
     * 是否有下一关 或者是已经通关
     *
     * @return
     */
    private boolean judegAppPassed() {
        return (mCurrentStageIndex == Const.SONG_INFO.length - 1);
    }

    /**
     * 设置答案
     *
     * @param wordButton
     */
    private void setSelectWord(WordButton wordButton) {
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            if (mBtnSelectWords.get(i).mWordString.length() == 0) {
                // 设置答案文字框内容及可见性
                mBtnSelectWords.get(i).mViewButton.setText(wordButton.mWordString);
                mBtnSelectWords.get(i).mIsVisisable = true;
                mBtnSelectWords.get(i).mWordString = wordButton.mWordString;
                //记录索引
                mBtnSelectWords.get(i).mIndex = wordButton.mIndex;
                //log
                LogUtils.e("", wordButton.mIndex + "");
                setButtonVisiable(wordButton, View.INVISIBLE);

                break;
            }
        }

    }

    /**
     * 清除已选框的
     *
     * @param wordButton
     */
    private void clearTheAnswer(WordButton wordButton) {
        wordButton.mViewButton.setText("");
        wordButton.mWordString = "";
        wordButton.mIsVisisable = false;
        //  设置待选框的可见性
        setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
        //设置白颜色
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
        }
    }

    /**
     * 设置待选文字框是否可见
     *
     * @param wordButton
     * @param visibility
     */

    private void setButtonVisiable(WordButton wordButton, int visibility) {
        wordButton.mViewButton.setVisibility(visibility);
        wordButton.mIsVisisable = (visibility == View.VISIBLE) ? true : false;

    }

    /**
     * 检查答案
     *
     * @return
     */
    private int cheackTheAnswer() {
        //先检查长度
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            //如果有空的说明是不完整的
            if (mBtnSelectWords.get(i).mWordString.length() == 0) {
                return STATUS_ANSWER_LACK;
            }

        }
        // 答案完整，继续检查正确性
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            sb.append(mBtnSelectWords.get(i).mWordString);
        }
        return (sb.toString().equals(mCurrentSong.getSongName())) ? STATUS_ANSWER_RIGHT : STATUS_ANSWER_WRONG;
    }


    /**
     * 闪烁文字
     */
    private void sparkTheWords() {
        //定时器
        TimerTask timerTask = new TimerTask() {
            boolean mChange = false;
            int mSpardTimes = 0;

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (++mSpardTimes > SPARD_TIME) {
                            return;
                        }
                        //执行闪烁文字 白红交替
                        for (int i = 0; i < mBtnSelectWords.size(); i++) {
                            mBtnSelectWords.get(i).mViewButton.setTextColor(mChange ? Color.RED : Color.WHITE);
                        }
                        mChange = !mChange;
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1, 150);

    }

    /**
     * 自动选择一个答案
     */
    private void tipAnswer() {
        //找答案
        boolean tipWord = false;
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            if (mBtnSelectWords.get(i).mWordString.length() == 0) {
                //根据当前的答案框条件选择对应的文字并填入

                if (!handleCoins(-getTipWordCoins())) {
                    //金币数量不够 显示对话框
                    showConfirmDialog(ID_DIALOG_LACK_COINS);
                    return;
                } else {
                    onWordButtonClick(findAnswerWord(i));
                    tipWord = true;
                }
                break;
            }
        }
        // 没有找到可以填充的答案
        if (!tipWord) {
            //   闪烁文字提示用户
            sparkTheWords();
        }

    }

    /**
     * 找个一个答案的文字，并且当前是可见的
     *
     * @return index 当前需要填入答案框的索引
     */
    private WordButton findAnswerWord(int index) {
        WordButton buf = null;
        for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
            buf = mAllWords.get(i);
            if (buf.mWordString.equals("" + mCurrentSong.getNameCharacters()[index])) {
                return buf;
            }

        }
        return null;
    }

    /**
     * 删除文字及金币
     */
    private void deleteOneWord() {
        //减少金币
        if (!handleCoins(-getDeleteWordCoins())) {
            //金币不够 显示对话框
            showConfirmDialog(ID_DIALOG_LACK_COINS);
            return;
        }
        //将这个索引对应的wordButton 设置成不可见
        setButtonVisiable(findNotAnswerWord(), View.INVISIBLE);

    }

    /**
     * 找个一个不是答案的文件，并且当前是可见的
     *
     * @return
     */
    private WordButton findNotAnswerWord() {
        Random random = new Random();
        WordButton buf = null;
        while (true) {
            int index = random.nextInt(MyGridView.COUNTS_WORDS);
            buf = mAllWords.get(index);
            if (buf.mIsVisisable && !isTheAnswerWord(buf)) {
                return buf;
            }
        }
    }

    private boolean isTheAnswerWord(WordButton button) {
        boolean result = false;
        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
            if (button.mWordString.equals("" + mCurrentSong.getNameCharacters()[i])) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 增加或者减少指定数量的金币
     *
     * @param data
     * @return true 增加减少 false 失败
     */
    private boolean handleCoins(int data) {
        //判断当前总数量金币的减少
        if (mCurrentCoins + data >= 0) {
            mCurrentCoins += data;
            mViewCurrentCoins.setText(mCurrentCoins + "");
            return true;
        } else {
            //金币不够
            return false;
        }
    }

    /**
     * 从配置文件中读取删除操作所要的金币
     *
     * @return
     */
    private int getDeleteWordCoins() {
        return this.getResources().getInteger(R.integer.pay_delete_word);
    }

    /**
     * 从配置文件中读取提示操作所要的金币
     *
     * @return
     */
    private int getTipWordCoins() {
        return this.getResources().getInteger(R.integer.pay_tip_answer);
    }

    /**
     * 处理删除待选文字事件
     */
    private void handledeleteWord() {
        ImageButton button = $(R.id.btn_delete_word);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteOneWord();
                showConfirmDialog(ID_DIALOG_DELETE_WORD);
            }
        });
    }

    /**
     * 处理按键事件
     */
    private void handleTipAnswer() {
        ImageButton button = $(R.id.btn_tip_answer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(ID_DIALOG_TIP_ANSWER);
//                tipAnswer();
            }
        });

    }

    /*
    * 自定义AliertDialog 事件响应
    * */
    //删除错误答案
    private IAlerDialogButtonListener mbtnOkDeleteWordlistener =
            new IAlerDialogButtonListener() {
                @Override
                public void onClick() {
                    //执行事件
                    deleteOneWord();
                }
            };

    // 答案提示
    private IAlerDialogButtonListener mbtnOkTipAnswerlistener =
            new IAlerDialogButtonListener() {
                @Override
                public void onClick() {
                    //执行事件
                    tipAnswer();
                }
            };
    //金币不足
    private IAlerDialogButtonListener mbtnOklackCoinslistener =
            new IAlerDialogButtonListener() {
                @Override
                public void onClick() {
                    //执行事件
                }
            };

    private void showConfirmDialog(int id) {
        switch (id) {
            case ID_DIALOG_DELETE_WORD:
                Dialogutils.showDialog(MainActivity.this, "确认花掉" + getDeleteWordCoins() +
                        "个金币去掉一个错误答案", mbtnOkDeleteWordlistener);
                break;
            case ID_DIALOG_TIP_ANSWER:
                Dialogutils.showDialog(MainActivity.this, "确认花掉" + getTipWordCoins() +
                        "个金币获得一个文字提示", mbtnOkTipAnswerlistener);
                break;
            case ID_DIALOG_LACK_COINS:
                Dialogutils.showDialog(MainActivity.this, "金币不足，去商店补充", mbtnOklackCoinslistener);
                break;
        }
    }
}
