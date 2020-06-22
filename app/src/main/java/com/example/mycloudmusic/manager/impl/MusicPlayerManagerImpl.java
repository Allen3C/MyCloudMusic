package com.example.mycloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.listener.Consumer;
import com.example.mycloudmusic.listener.MusicPlayerListener;
import com.example.mycloudmusic.manager.MusicPlayerManager;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.HandlerUtil;
import com.example.mycloudmusic.util.ListUtil;
import com.example.mycloudmusic.util.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mycloudmusic.util.Constant.MESSAGE_PROGRESS;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager, MediaPlayer.OnCompletionListener {


    /**
     * 单例实例对象
     */
    private static MusicPlayerManagerImpl instance;
    /**
     * 上下文
     */
    private final Context context;
    private final MediaPlayer player;
    /**
     * 当前播放的音乐对象
     */
    private Song data;

    /**
     * 播放器状态监听器
     */
    private List<MusicPlayerListener> listeners = new ArrayList<>();
    private static final String TAG = "MusicPlayerManagerImpl";
    /**
     * 定时器任务
     */
    private TimerTask timerTask;
    private Timer timer;


    /**
     * 私有构造方法
     *
     * 这里外部就不能通过new方法来创建对象了
     * @param context
     */
    private MusicPlayerManagerImpl(Context context) {
        //保存context
        //因为后面可能用到
        this.context = context;

        //初始化播放器
        player = new MediaPlayer();

        //设置播放监听器
        initPlayerListener();
    }

    /**
     * 设置播放器监听器
     */
    private void initPlayerListener() {
        //设置播放器准备监听器
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /**
             * 播放器准备开始播放
             *
             * 这里可以获取到音乐时长
             * 如果是视频还能获取到视频宽高等信息
             * @param mediaPlayer
             */
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                LogUtil.d(TAG,"onPrepared");

                //将音乐总时长保存到音乐对象
                data.setDuration(mediaPlayer.getDuration());

                //回调监听器
                ListUtil.eachListener(listeners, listener->listener.onPrepared(mediaPlayer,data));
            }
        });

        //设置播放完毕监听器
        player.setOnCompletionListener(this);
    }

    /**
     * 获取单例对象
     *
     * getInstance：方法名可以随便取
     * 只是在Java这边大部分项目都取这个名字
     * @param context
     * @return
     */
    public static synchronized MusicPlayerManager getInstance(Context context) {
        if (instance == null) {
            //如果为空
            //就创建实现类
            instance = new MusicPlayerManagerImpl(context);
        }

        //返回对象
        return instance;
    }

    @Override
    public void play(String uri, Song data) {
        try {
            //保存音乐对象
            this.data = data;

            //释放player
            player.reset();

            //设置数据源
            player.setDataSource(uri);

            //同步准备
            //真实项目中可能会使用异步
            //因为如果网络不好
            //同步可能会卡主
            player.prepare();

            //开始播放
            player.start();

            //回调监听器
            publishPlayingStatus();

            //启动播放进度通知
            startPublishProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布播放中状态
     */
    private void publishPlayingStatus() {
//        for (MusicPlayerListener listener : listeners) {
//            listener.onPlaying(data);
//        }
        //使用重构后的方法
        ListUtil.eachListener(listeners, new Consumer<MusicPlayerListener>() {
            @Override
            public void accept(MusicPlayerListener listener) {
                listener.onPlaying(data);
            }
        });
    }
    /**
     * 发布暂停状态
     */
    private void publishPausedStatus() {
//        for (MusicPlayerListener listener : listeners) {
//            listener.onPaused(data);
//        }
        //使用重构后的方法
        ListUtil.eachListener(listeners, new Consumer<MusicPlayerListener>() {
            @Override
            public void accept(MusicPlayerListener listener) {
                listener.onPaused(data);
            }
        });
    }


    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        if (isPlaying()) {
            //如果在播放就暂停
            player.pause();

            //回调监听器
            publishPausedStatus();

            //通知通知进度
            stopPublishProgress();
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            //如果没有播放就播放
            player.start();

            //回调监听器
            publishPlayingStatus();

            //启动播放进度通知
            startPublishProgress();
        }
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }

        //启动进度通知
        startPublishProgress();
    }

    @Override
    public void removeMusicPlayerListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Song getData() {
        return data;
    }

    @Override
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    @Override
    public void setLooping(boolean looping) {
        player.setLooping(looping);
    }

    /**
     * 启动播放进度通知
     */
    private void startPublishProgress() {
        if (isEmptyListeners()) {
            //没有进度回调就不启动
            return;
        }

        if (timerTask != null) {
            //已经启动了
            return;
        }

        //创建一个任务
        timerTask = new TimerTask() {

            @Override
            public void run() {
                //如果没有监听器了就停止定时器
                if (isEmptyListeners()) {
                    stopPublishProgress();
                    return;
                }

                LogUtil.d(TAG, "time task progress");

                //这里是子线程
                //不能直接操作UI
                //为了方便外部
                //在内部就切换到主线程
                //HandlerUtil.getHandler().obtainMessage(MESSAGE_PROGRESS).sendToTarget();
                handler.obtainMessage(MESSAGE_PROGRESS).sendToTarget();
            }
        };

        //创建一个定时器
        timer = new Timer();

        //启动一个持续的任务

        //16毫秒
        //为什么是16毫秒？
        //因为后面我们要实现卡拉OK歌词
        //为了画面的连贯性
        //应该保持1秒60帧
        //所以1/60；就是一帧时间
        //如果没有卡拉OK歌词
        //那么每秒钟刷新一次即可
        timer.schedule(timerTask, 0, Constant.DEFAULT_TIME);
    }

    /**
     * 是否没有进度监听器
     * @return
     */
    private boolean isEmptyListeners() {
        return listeners.size() == 0;
    }

    /**
     * 停止播放进度通知
     */
    private void stopPublishProgress() {
        //停止定时器任务
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        //停止定时器
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 创建一个Handler
     * 用来将事件转换到主线程
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_PROGRESS:
                    //播放进度回调

                    //将进度设置到音乐对象
                    data.setProgress(player.getCurrentPosition());
                    //回调监听
                    ListUtil.eachListener(listeners,  listener -> listener.onProgress(data));
                    break;
            }
        }
    };

    /**
     * 音乐播放完毕了监听器
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        //回调完成监听器
        ListUtil.eachListener(listeners, listener->listener.onCompletion(mp));
    }
}
