package com.example.mycloudmusic.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.adapter.SimplePlayerAdapter;
import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.listener.MusicPlayerListener;
import com.example.mycloudmusic.manager.ListManager;
import com.example.mycloudmusic.manager.MusicPlayerManager;
import com.example.mycloudmusic.service.MusicPlayerService;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.NotificationUtil;
import com.example.mycloudmusic.util.TimeUtil;
import com.example.mycloudmusic.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.mycloudmusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mycloudmusic.util.Constant.MODEL_LOOP_RANDOM;

/**
 * 简单播放器实现
 */
public class SimplePlayerActivity extends BaseTitleActivity implements SeekBar.OnSeekBarChangeListener, MusicPlayerListener {
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
    private MusicPlayerManager musicPlayerManager;
    private ListManager listManager;
    private SimplePlayerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //高度固定
        rv.setHasFixedSize(true);

        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
//        //使用MusicPlayerService获取播放管理器
//        MusicPlayerManager o1 = MusicPlayerService.getMusicPlayerManager(getMainActivity());
//        MusicPlayerManager o2 = MusicPlayerService.getMusicPlayerManager(getMainActivity());
//        LogUtil.d(TAG, "" + (o1 == o2));

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());
        //获取播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getApplicationContext());

//        //测试音乐播放
//        //由于现在没有获取数据
//        //所以创建一个测试数据
//        String songUrl = "http://dev-courses-misuc.ixuea.com/assets/s1.mp3";
//
//        Song song=new Song();
//        song.setUri(songUrl);
//
//        musicPlayerManager.play(songUrl, song);

        super.initDatum();
        //创建适配器
        adapter = new SimplePlayerAdapter(android.R.layout.simple_list_item_1);

        //设置到控件
        rv.setAdapter(adapter);

        //设置数据
        adapter.replaceData(listManager.getDatum());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置进度条监听器
        sb_progress.setOnSeekBarChangeListener(this);

        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获取这首音乐
                Song data = listManager.getDatum().get(position);

                //播放音乐
                listManager.play(data);
            }
        });
    }

    /**
     * 进入了前台,界面可见
     */
    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");

        //设置播放监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //显示初始化数据
        showInitData();

        //显示播放进度
        showProgress();

        //显示音乐时长
        showDuration();

        //显示播放状态
        showMusicPlayStatus();

        //选中当前音乐
        scrollPosition();
    }
    /**
     * 进入了后台,界面不可见
     */
    @Override
    protected void onPause() {
        super.onPause();

        //取消播放器监听器
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    /**
     * 上一曲点击
     */
    @OnClick(R.id.bt_previous)
    public void onPreviousClick() {
        LogUtil.d(TAG, "onPreviousClick");
        listManager.play(listManager.previous());
    }

    /**
     * 播放点击
     */
    @OnClick(R.id.bt_play)
    public void onPlayClick() {
//        LogUtil.d(TAG, "onPlayClick");
//        //测试通知渠道
//        //该通知没有任何实际意义
//
//        //获取通知
//        Notification notification= NotificationUtil.getServiceForeground(getApplicationContext());
//        //显示通知
//        //Id没什么实际意义
//        //只是相同Id的通知会被替换
//        NotificationUtil.showNotification(100, notification);

        playOrPause();
    }

    /**
     * 播放或者暂停
     */
    private void playOrPause() {
        if (musicPlayerManager.isPlaying()) {
            listManager.pause();
        } else {
            listManager.resume();
        }
    }

    /**
     * 下一曲点击
     */
    @OnClick(R.id.bt_next)
    public void onNextClick() {
        LogUtil.d(TAG, "onNextClick");

        //获取下一首音乐
        Song data = listManager.next();

        if (data != null) {
            listManager.play(data);
        } else {
            //正常情况下不能能走到这里
            //因为播放界面只有播放列表有数据时才能进入
            ToastUtil.errorShortToast(R.string.not_play_music);
        }

    }

    /**
     * 音乐循环模式点击
     */
    @OnClick(R.id.bt_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");

        //改变循环模式
        listManager.changeLoopModel();

        //显示循环模式
        showLoopModel();
    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        //获取当前循环模式
        int model = listManager.getLoopModel();

        //根据不同循环模式
        //显示不同的提示
        switch (model) {
            case MODEL_LOOP_LIST:
                bt_loop_model.setText("列表循环");
                break;
            case MODEL_LOOP_RANDOM:
                bt_loop_model.setText("随机模式");
                break;
            default:
                bt_loop_model.setText("单曲循环");
                break;
        }
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

        if (fromUser) {
            //跳转到该位置播放
            musicPlayerManager.seekTo(progress);
        }
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


    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        bt_play.setText("播放");
    }
    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        bt_play.setText("暂停");
    }

    /**
     * 显示音乐播放状态
     */
    private void showMusicPlayStatus(){
        if (musicPlayerManager.isPlaying()){
            showPauseStatus();
        } else {
            showPlayStatus();
        }
    }

    /**
     * 显示时长
     */
    private void showDuration() {
        //获取当前正在播放的音乐
        long end = musicPlayerManager.getData().getDuration();

        //将毫秒格式化为分钟:秒
        tv_end.setText(TimeUtil.formatMinuteSecond((int) end));

        //设置到进度条
        sb_progress.setMax((int) end);
    }

    /**
     * 显示播放进度
     */
    private void showProgress() {
        //获取播放进度
        long progress = musicPlayerManager.getData().getProgress();
        //格式化进度
        tv_start.setText(TimeUtil.formatMinuteSecond((int) progress));

        //设置到进度条
        sb_progress.setProgress((int) progress);
    }

    /**
     * 显示初始化数据
     */
    private void showInitData() {
        //获取当前播放的音乐
        Song data = listManager.getData();
        //显示标题
        tv_title.setText(data.getTitle());
    }

    //播放管理器监听器
    @Override
    public void onPaused(Song data) {
        LogUtil.d(TAG, "onPaused");

        showPlayStatus();
    }
    @Override
    public void onPlaying(Song data) {
        LogUtil.d(TAG, "onPlaying");

        showPauseStatus();
    }

    @Override
    public void onPrepared(MediaPlayer mp, Song data) {
        LogUtil.d(TAG, "onPrepared:" + data.getDuration());

        //显示初始化数据
        showInitData();

        //显示时长
        showDuration();

        //选中当前音乐
        scrollPosition();
    }

    /**
     * 滚动到当前音乐位置
     */
    private void scrollPosition() {
        //选中当前播放的音乐

        //使用post方法是
        //将方法放到了消息循环
        //如果不这样做
        //在onCreate这样的方法中滚动无效
        //因为这时候列表的数据还没有显示完成
        //具体的这部分我们在《详解View》课程中讲解了
        rv.post(new Runnable() {
            @Override
            public void run() {
                //获取当前音乐位置
                int index = listManager.getDatum().indexOf(listManager.getData());

                if (index != -1) {
                    //滚动到该位置
                    rv.smoothScrollToPosition(index);

                    //选中
                    adapter.setSelectedIndex(index);
                }

            }
        });
    }

    @Override
    public void onProgress(Song data) {
        LogUtil.d(TAG, "onProgress:" + data.getProgress() + "," + data.getDuration());

        showProgress();
    }

//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        LogUtil.d(TAG, "onCompletion");
//    }
    //end 播放管理器监听器
}
