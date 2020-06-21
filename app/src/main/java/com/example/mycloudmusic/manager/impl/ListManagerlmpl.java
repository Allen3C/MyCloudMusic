package com.example.mycloudmusic.manager.impl;

import android.content.Context;

import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.manager.ListManager;
import com.example.mycloudmusic.manager.MusicPlayerManager;
import com.example.mycloudmusic.service.MusicPlayerService;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ResourceUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 列表接口默认实现类
 */
public class ListManagerlmpl implements ListManager {

    private static final String TAG = "ListManagerlmpl";
    private static ListManagerlmpl instance;
    private final Context context;

    /**
     * 播放列表
     */
    private List<Song> datum = new LinkedList<>();

    /**
     * 当前音乐对象
     */
    private Song data;
    private final MusicPlayerManager musicPlayerManager;
    /**
     * 是否播放了
     */
    private boolean isPlay;

    private ListManagerlmpl(Context context) {
        this.context = context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);
    }


    /**
     * 获取单例对象
     *
     * @param context
     * @return
     */
    public static synchronized ListManagerlmpl getInstance(Context context) {
        if (instance == null) {
            //只有没有初始化才创建对象
            instance = new ListManagerlmpl(context);
        }
        return instance;
    }

    @Override
    public void setDatum(List<Song> datum) {
        LogUtil.d(TAG,"setDatum");
        //清空原来的数据
        this.datum.clear();

        //添加新的数据
        this.datum.addAll(datum);
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return datum;
    }

    @Override
    public void play(Song data) {
        LogUtil.d(TAG,"play");

        //标记已经播放了
        isPlay = true;

        //保存数据
        this.data = data;

        //播放音乐
        musicPlayerManager.play(ResourceUtil.resourceUri(data.getUri()), data);
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"pause");
        musicPlayerManager.pause();
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");

        if (isPlay) {
            //原来已经播放过
            //也就说播放器已经初始化了
            musicPlayerManager.resume();
        } else {
            //到这里，是应用开启后，第一次点继续播放
            //而这时内部其实还没有准备播放，所以应该调用播放
            play(data);
        }
    }
}
