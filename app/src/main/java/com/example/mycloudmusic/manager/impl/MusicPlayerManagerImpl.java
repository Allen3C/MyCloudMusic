package com.example.mycloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.listener.MusicPlayerListener;
import com.example.mycloudmusic.manager.MusicPlayerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布播放中状态
     */
    private void publishPlayingStatus() {
        for (MusicPlayerListener listener : listeners) {
            listener.onPlaying(data);
        }
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
            publishPlayingStatus();
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            //如果没有播放就播放
            player.start();

            //回调监听器
            publishPlayingStatus();
        }
    }
}
