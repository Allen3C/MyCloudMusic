package com.example.mycloudmusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 简单播放器实现
 */
public class SimplePlayerActivity extends BaseTitleActivity implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "SimplePlayerActivity";
    /**
     * 列表
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 音乐标题
     */
    @BindView(R.id.tv_title)
    TextView tv_title;

    /**
     * 音乐播放进度
     */
    @BindView(R.id.tv_start)
    TextView tv_start;

    /**
     * 进度条
     */
    @BindView(R.id.sb_progress)
    SeekBar sb_progress;

    /**
     * 音乐总时长
     */
    @BindView(R.id.tv_end)
    TextView tv_end;

    /**
     * 播放按钮
     */
    @BindView(R.id.bt_play)
    Button bt_play;

    /**
     * 循环模式按钮
     */
    @BindView(R.id.bt_loop_model)
    Button bt_loop_model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置进度条监听器
        sb_progress.setOnSeekBarChangeListener(this);
    }

    /**
     * 上一曲点击
     */
    @OnClick(R.id.bt_previous)
    public void onPreviousClick() {
        LogUtil.d(TAG, "onPreviousClick");
    }

    /**
     * 播放点击
     */
    @OnClick(R.id.bt_play)
    public void onPlayClick() {
        LogUtil.d(TAG, "onPlayClick");
    }

    /**
     * 下一曲点击
     */
    @OnClick(R.id.bt_next)
    public void onNextClick() {
        LogUtil.d(TAG, "onNextClick");

    }

    /**
     * 音乐循环模式点击
     */
    @OnClick(R.id.bt_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");
    }

    /**
     * 启动界面方法
     * @param activity 宿主activity
     */
    public static void start(Activity activity) {
        //创建意图
        //意图：就是要干什么
        Intent intent = new Intent(activity, SimplePlayerActivity.class);

        //启动界面
        activity.startActivity(intent);
    }

    /**
     * 进度条改变了
     *
     * @param seekBar
     * @param progress
     * @param fromUser 是否是用户触发的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LogUtil.d(TAG, "onProgressChanged");

//        if (fromUser) {
//            //if (seekBar.getId() == R.id.sb_volume) {
//            //    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//            //} else {
//            musicPlayerManager.seekTo(progress);
//            if (!musicPlayerManager.isPlaying()) {
//                musicPlayerManager.resume();
//            }
//            //}
//
//        }
    }

    /**
     * 开始拖拽进度条
     *
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStartTrackingTouch");
    }

    /**
     * 停止拖拽进度条
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStopTrackingTouch");
    }
}
